package com.workshop.easilytestable.api.order.dto;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateOrderResponse {
    @NonNull
    UUID orderId;
}
