package com.workshop.easilytestable.domain.order;

import com.workshop.easilytestable.api.order.dto.CreateOrderRequest;
import com.workshop.easilytestable.domain.gold.ExchangeRate;
import com.workshop.easilytestable.domain.gold.ExchangeRateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderFactory {

    private final OrderRepository orderRepository;
    private final ExchangeRateProvider exchangeRateProvider;
    private final OrderCreatedEventSender orderCreatedEventSender;

    public UUID create(CreateOrderRequest orderRequest) {
        ExchangeRate recentRate = exchangeRateProvider.getRecentRate();
        Order order = toOrder(orderRequest, UUID.randomUUID(), recentRate.getValue());
        UUID savedOrderId = orderRepository.insert(order);
        orderCreatedEventSender.send(OrderCreatedEvent.of(order));
        return savedOrderId;
    }

    Order toOrder(CreateOrderRequest orderRequest, UUID id, BigDecimal price) {
        return new Order(
                id,
                orderRequest.getAmount(),
                orderRequest.getAmount().multiply(price),
                orderRequest.getTaxRate(),
                orderRequest.getSubmissionDate(),
                orderRequest.getComment(),
                OrderStatus.ACCEPTED
        );
    }
}
