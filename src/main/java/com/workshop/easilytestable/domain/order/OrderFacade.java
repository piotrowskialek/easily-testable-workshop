package com.workshop.easilytestable.domain.order;

import com.workshop.easilytestable.domain.gold.ExchangeRate;
import com.workshop.easilytestable.domain.gold.ExchangeRateProvider;
import com.workshop.easilytestable.domain.order.exception.OrderNotFoundException;
import io.vavr.collection.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class OrderFacade {

    private final ExchangeRateProvider exchangeRateProvider;
    private final OrderStorage orderStorage;
    private final OrderCreatedEventSender orderCreatedEventSender;

    public UUID create(@NonNull CreateOrderCommand createOrderCommand) {
        ExchangeRate recentRate = exchangeRateProvider.getRecentRate();
        Order order = Order.of(UUID.randomUUID(), recentRate, createOrderCommand);
        UUID savedOrderId = orderStorage.insert(order);
        orderCreatedEventSender.send(OrderCreatedEvent.of(order));
        return savedOrderId;
    }

    public Order getOrderById(@NonNull UUID id) {
        return orderStorage.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public List<Order> getOrdersByDateRange(@NonNull Instant from, @NonNull Instant to) {
        return orderStorage.findByDateRange(from, to);
    }
}
