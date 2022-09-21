package com.workshop.easilytestable.util

import groovy.json.JsonSlurper
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.utils.KafkaTestUtils

trait KafkaAbility {

    Consumer<String, String> orderCreatedEventConsumer

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker

    Consumer<String, String> configureConsumer() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker)
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<String, String>(consumerProps).createConsumer()
        consumer.subscribe(Collections.singleton("orderCreated"))
        return consumer;
    }

    Map consumeOrderCreatedEvent() {
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(orderCreatedEventConsumer, "orderCreated")
        def parsedEvent = new JsonSlurper().parseText(singleRecord.value())
        return parsedEvent as Map
    }
}
