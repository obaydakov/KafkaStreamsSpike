/opt/kafka_2.11-0.10.0.0/bin/kafka-topics.sh --create \
          --zookeeper zk:2181 \
          --replication-factor 1 \
          --partitions 1 \
          --topic test.input
