package com.tomogle.kafkastreamsspike

import java.lang.Long
import java.util.Properties
import java.util.regex.Pattern

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, KeyValue, StreamsConfig}
import org.apache.kafka.streams.kstream._

object WordCountApp {

  def main(args: Array[String]): Unit = {
    val configuration = new Properties()
    configuration.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-spike")
    // Kafka brokers
    configuration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    // ZooKeeper ensemble
    configuration.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181")
    // Default serializers and deserializers for keys and values
    configuration.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass.getName)
    configuration.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass.getName)

    val stringSerde = Serdes.String()

    val longSerde = Serdes.Long()
    val builder = new KStreamBuilder()

    val lines: KStream[String, String] = builder.stream(InputTopic)
    // Define processing topology
    val wordCountTable = calculateWordCounts(lines)
    wordCountTable.toStream.to(stringSerde, longSerde, OutputTopic)

    val streams = new KafkaStreams(builder, configuration)
    streams.start()
    sys.runtime addShutdownHook {
      new Thread {
        override def run(): Unit = {
          streams.close()
        }
      }
    }

  }

  val InputTopic = "test.input"
  val OutputTopic = "test.output"

  val WordPattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS)

  def calculateWordCounts(lines: KStream[String, String]): KTable[String, Long] = {
    import KafkaStreamsConversions._
    import collection.JavaConverters._

    val words: KStream[String, String] = lines.flatMapValues { (line: String) =>
      WordPattern.split(line).toIterable.asJava
    }
    val myMapper: (String, String) => KeyValue[String, String] = { (key: String, word: String) =>
      new KeyValue(word, word)
    }
    val ps: KStream[String, String] = words.map(myMapper).through("RekeyedIntermediateTopic")
    ps.countByKey("WordCountTable")
  }

}
