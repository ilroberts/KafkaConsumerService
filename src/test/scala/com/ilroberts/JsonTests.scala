package com.ilroberts

import org.scalatest.{FlatSpec, Matchers}
import spray.json.DefaultJsonProtocol

object MessageJsonProtocol extends DefaultJsonProtocol {
  implicit val headerFormat = jsonFormat1(MessageHeader)
  implicit val bodyFormat = jsonFormat1(MessageBody)
  implicit val messageFormat = jsonFormat2(BasicMessage)
}

case class MessageHeader(messageType: String)
case class MessageBody(field1: String)
case class BasicMessage(header: MessageHeader, body: MessageBody)

class JsonTests extends FlatSpec with Matchers {

  "A message" should "have a header" in {

    import MessageJsonProtocol._
    import spray.json._

    val jsonString =
      """
        |{
        |   "header": {
        |     "messageType": "exampleMessageType"
        |   },
        |   "body": {
        |     "field1": "field1Value"
        |   }
        |}
      """.stripMargin

    val message = jsonString.parseJson.convertTo[BasicMessage]
    println(message.header.messageType)
    assert(message.header.messageType == "exampleMessageType")

  }

}
