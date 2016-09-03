/opt/kafka_2.11-0.10.0.0/bin/kafka-console-consumer.sh --zookeeper zk:2181 \
          --topic test.output \
          --from-beginning \
          --formatter kafka.tools.DefaultMessageFormatter \
          --property print.key=true \
          --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
          --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
