package com.workshop.easilytestable.api.order.dto;

import com.workshop.easilytestable.domain.order.Order;
import com.workshop.easilytestable.domain.order.OrderStatus;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static io.vavr.API.None;

@Value
public class CreateOrderRequest {

    @NonNull
    BigDecimal amount;

    @NonNull
    BigDecimal taxRate;

    @NonNull
    Instant submissionDate;

    @NonNull
    Option<String> comment;

    @NonNull
    String status;


    public Order toOrder(UUID id, BigDecimal value) {
        return new Order(
                id,
                amount,
                value,
                taxRate,
                submissionDate,
                comment,
                OrderStatus.valueOf(status)
                );
    }
}
