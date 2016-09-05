FROM lwieske/java-8:jre-8u102
COPY target/scala-2.11/kafka-streams-spike-assembly-1.0.jar /opt/kafka-streams-spike/kafka-streams-spike-assembly.jar
ENTRYPOINT [ "java",  "-jar" ]
CMD [ "/opt/kafka-streams-spike/kafka-streams-spike-assembly.jar" ]
