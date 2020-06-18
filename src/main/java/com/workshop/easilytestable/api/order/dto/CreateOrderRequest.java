package com.workshop.easilytestable.api.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workshop.easilytestable.domain.order.Order;
import com.workshop.easilytestable.domain.order.OrderStatus;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
public class CreateOrderRequest {

    @NonNull
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    BigDecimal amount;

    @NonNull
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    BigDecimal taxRate;

    @NonNull
    Instant submissionDate;

    @NonNull
    Option<String> comment;
}
