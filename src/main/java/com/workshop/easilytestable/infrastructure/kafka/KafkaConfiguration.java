package com.workshop.easilytestable.infrastructure.kafka;

import com.workshop.easilytestable.adapters.kafka.KafkaOrderEventSender;
import com.workshop.easilytestable.domain.order.OrderCreatedEvent;
import com.workshop.easilytestable.domain.order.OrderCreatedEventSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfiguration {
    @Bean
    OrderCreatedEventSender kafkaOrderEventSender(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        return new KafkaOrderEventSender(kafkaTemplate);
    }
}
