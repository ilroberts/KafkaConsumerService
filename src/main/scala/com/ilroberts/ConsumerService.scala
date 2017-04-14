package com.ilroberts

import java.util.concurrent._

import akka.actor.{ActorSystem, Props}
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.ilroberts.Messages.Person
import com.typesafe.config.ConfigFactory
import kafka.utils.Logging
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}
import spray.json._

class ConsumerService(val brokers: String,
                      val groupId: String,
                      val topic: String) extends Logging {

  implicit val system = ActorSystem("ConsumerSystem")
  var executor: ExecutorService = _

  def shutdown(): Unit = {
    if (executor != null)
      executor.shutdown()
  }

  def run() = {
    Executors.newSingleThreadExecutor.execute(new Runnable {
      override def run(): Unit = {

        import DefaultJsonProtocol._

        implicit val personFormat = jsonFormat1(Person)
        val helloActor = system.actorOf(Props[RoutingActor])
        implicit val materializer = ActorMaterializer()

        val consumerSettings = ConsumerSettings(
          system, new ByteArrayDeserializer, new StringDeserializer).withBootstrapServers(brokers).withGroupId(groupId)

        Consumer.atMostOnceSource(consumerSettings, Subscriptions.topics(topic)).map(record => record.value)
        .to(Sink.foreach(v => {
          val person = v.parseJson.convertTo[Person]
          helloActor ! person
        })).run()

      }
    })
  }
}

object ConsumerService extends App {

  val configFactory = ConfigFactory.load()
  val brokers = configFactory.getString("consumer.brokers")
  val groupId = configFactory.getString("consumer.groupId")
  val topic = configFactory.getString("consumer.topic")

  val example = new ConsumerService(brokers, groupId, topic)

  example.run()
}
