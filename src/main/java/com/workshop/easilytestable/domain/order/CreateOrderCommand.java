package com.workshop.easilytestable.domain.order;

import io.vavr.control.Option;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
public class CreateOrderCommand {

    @NonNull
    BigDecimal amount;

    @NonNull
    BigDecimal taxRate;

    @NonNull
    Instant submissionDate;

    @NonNull
    Option<String> comment;
}
