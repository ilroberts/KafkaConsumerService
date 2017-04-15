package com.ilroberts

object Messages {


  case class MessageHeader(messageType: String)
  case class MessageBody(body: String)
  case class BasicMessage(header: MessageHeader)

  case class Person(name: String)

}
