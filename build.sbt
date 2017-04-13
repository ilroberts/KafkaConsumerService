name := "Kafka"

version := "1.0"

scalaVersion := "2.11.9"

// https://mvnrepository.com/artifact/org.apache.kafka/kafka_2.11
libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.2.0"

// https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.0"

// https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "0.10.2.0"
