package com.tomogle.kafkastreamsspike

import org.apache.kafka.streams.kstream.{KeyValueMapper, ValueMapper}

object KafkaStreamsConversions {

  implicit def convertValueMapper[A, B](function: A => B): ValueMapper[A, B] = new ValueMapper[A, B] {
    override def apply(value: A): B = function(value)
  }

  implicit def convertKeyValueMapper[K, V, R](function: (K, V) => R): KeyValueMapper[K, V, R] = new KeyValueMapper[K, V, R] {
    override def apply(key: K, value: V): R = function(key, value)
  }
}

