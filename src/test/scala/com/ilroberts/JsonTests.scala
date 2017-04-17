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

  "A message" should "have a header" in {

    val message = jsonString.parseJson.convertTo[BasicMessage]
    println(message.header.messageType)
    assert(message.header.messageType == "exampleMessageType")

  }

  "A message" should "have a message type" in {

    val jsonAst = jsonString.parseJson
    println(jsonAst.prettyPrint)


    val message = jsonAst.asJsObject.fields.foreach(f => println(f.toString()))
    val header = jsonAst.asJsObject.getFields("header")(0)
    val messageType = header.asJsObject.getFields("messageType")(0).toString().replace("\"", "")

    assert(messageType == "exampleMessageType")
  }
}
