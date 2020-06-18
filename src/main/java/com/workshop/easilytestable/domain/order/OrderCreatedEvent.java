package com.workshop.easilytestable.domain.order;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Value
public class OrderCreatedEvent {
    @NonNull
    UUID orderId;

    @NonNull
    BigDecimal amount;

    @NonNull
    BigDecimal value;

    @NonNull
    Instant submissionDate;

    static OrderCreatedEvent of(Order order) {
        return new OrderCreatedEvent(
                order.getId(),
                order.getAmount(),
                order.getValue(),
                order.getSubmissionDate()
        );
    }
}
