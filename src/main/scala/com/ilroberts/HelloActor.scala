package com.ilroberts

import akka.actor.Actor
import com.ilroberts.Messages.Person
import kafka.utils.Logging

class RoutingActor extends Actor with Logging {
  def receive = {
    case Person(name) => logger.info(name)
    case _ => logger.info("no idea what I just got")
  }
}

