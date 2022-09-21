package com.workshop.easilytestable.domain.order.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID orderId) {
        super(String.format("Not found order with id %s", orderId));
    }
}
