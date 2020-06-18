package com.workshop.easilytestable.domain.order;

import io.vavr.control.Option;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Value
public class Order {

    @NonNull
    UUID id;

    @NonNull
    BigDecimal amount;

    @NonNull
    BigDecimal value;

    @NonNull
    BigDecimal taxRate;

    @NonNull
    Instant submissionDate;

    @NonNull
    Option<String> comment;

    @NonNull
    OrderStatus status;
}
