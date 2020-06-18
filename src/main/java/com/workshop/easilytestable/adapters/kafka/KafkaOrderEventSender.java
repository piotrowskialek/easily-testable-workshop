package com.workshop.easilytestable.adapters.kafka;

import com.workshop.easilytestable.domain.order.OrderCreatedEvent;
import com.workshop.easilytestable.domain.order.OrderCreatedEventSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class KafkaOrderEventSender implements OrderCreatedEventSender {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Override
    public void send(OrderCreatedEvent event) {
        kafkaTemplate.sendDefault(event).addCallback(
                result -> log.info(String.format("OrderCreatedEvent with id: %s sent", event.getOrderId().toString())),
                log::error
        );
    }
}
