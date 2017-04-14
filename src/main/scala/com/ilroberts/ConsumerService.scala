package com.ilroberts

import java.util.concurrent._
import java.util.{Collections, Properties}

import akka.actor.{ActorSystem, Props}
import com.ilroberts.Messages.Person
import kafka.utils.Logging
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import spray.json._

import scala.collection.JavaConversions._

class ConsumerService(val brokers: String,
                      val groupId: String,
                      val topic: String) extends Logging {

  val system = ActorSystem("ConsumerSystem")

  val props = createConsumerConfig(brokers, groupId)
  val consumer = new KafkaConsumer[String, String](props)
  var executor: ExecutorService = _

  def shutdown(): Unit = {
    if (consumer != null)
      consumer.close()
    if (executor != null)
      executor.shutdown()
  }

  def createConsumerConfig(brokers: String, groupId: String): Properties = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props
  }

  case class Event(timestamp: String, eventType: String, actor: String, action: String, objects: List[String], message: String)

  def run() = {
    consumer.subscribe(Collections.singletonList(this.topic))


    Executors.newSingleThreadExecutor.execute(new Runnable {
      override def run(): Unit = {

        import DefaultJsonProtocol._

        implicit val personFormat = jsonFormat1(Person)
        val helloActor = system.actorOf(Props[RoutingActor])

        while (true) {
          val records = consumer.poll(1000)

          for (record <- records) {
            logger.info("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset())

            val person = record.value().parseJson.convertTo[Person]
            helloActor ! person


          }
        }
      }
    })
  }
}

object ConsumerService extends App {

  val example = new ConsumerService("localhost:9092", "group1", "test")
  example.run()

}
