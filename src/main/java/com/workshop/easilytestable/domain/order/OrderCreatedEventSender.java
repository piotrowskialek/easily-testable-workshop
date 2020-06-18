package com.workshop.easilytestable.domain.order;

public interface OrderCreatedEventSender {
    void send(OrderCreatedEvent event);
}
