name := "KafkaConsumerService"

version := "1.0"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/org.apache.kafka/kafka_2.11
libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.2.0"

// https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.0"

// https://mvnrepository.com/artifact/io.spray/spray-json_2.11
libraryDependencies += "io.spray" % "spray-json_2.11" % "1.3.3"

// https://mvnrepository.com/artifact/com.typesafe.akka/akka-actor_2.11
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.17"

// https://mvnrepository.com/artifact/com.typesafe/config
libraryDependencies += "com.typesafe" % "config" % "1.3.1"

libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka" % "0.14"

// https://mvnrepository.com/artifact/org.scalatest/scalatest_2.11
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.1"

