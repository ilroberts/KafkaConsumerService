package com.ilroberts

object Messages {

  sealed trait MessageComponents
  case class MessageHeader(messageType: String) extends MessageComponents
  case class MessageBody(body: String) extends MessageComponents


  sealed trait Message
  case class BasicMessage(MessageHeader, MessageBody) extends Message

  case class Person(name: String)

}
