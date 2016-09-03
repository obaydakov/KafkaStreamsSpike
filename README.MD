A spike for a Word Count stream processing app using Kafka Streams in Scala.

To run the WordCountApp:

Start up Zookeeper and Kafka by:

`cd scripts/`
`docker-compose up`

Use `docker ps` to get the container ID of your Kafka container.
Use `docker exec -it <container-ID> /bin/bash` to get a shell on the Kafka container.
