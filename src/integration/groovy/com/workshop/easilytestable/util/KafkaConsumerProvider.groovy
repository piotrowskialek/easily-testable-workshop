package com.workshop.easilytestable.util

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.stereotype.Component

@Component
class KafkaConsumerProvider {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker

    Consumer<String, String> configureConsumer() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker)
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<String, String>(consumerProps).createConsumer()
        consumer.subscribe(Collections.singleton("orderCreated"))
        return consumer;
    }
}
