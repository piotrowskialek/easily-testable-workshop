package com.workshop.easilytestable.adapters.kafka;

import com.workshop.easilytestable.domain.order.OrderCreatedEvent;
import com.workshop.easilytestable.domain.order.OrderCreatedEventSender;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderEventSender implements OrderCreatedEventSender {
    @Override
    public void send(OrderCreatedEvent event) {

    }
}
