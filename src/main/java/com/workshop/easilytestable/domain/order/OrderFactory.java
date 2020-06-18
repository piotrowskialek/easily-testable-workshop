package com.workshop.easilytestable.domain.order;

import com.workshop.easilytestable.api.order.dto.CreateOrderRequest;
import com.workshop.easilytestable.domain.gold.ExchangeRate;
import com.workshop.easilytestable.domain.gold.ExchangeRateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderFactory {

    private final OrderRepository orderRepository;
    private final ExchangeRateProvider exchangeRateProvider;

    public UUID create(CreateOrderRequest orderRequest) {
        ExchangeRate recentRate = exchangeRateProvider.getRecentRate();
        return orderRepository.insert(orderRequest.toOrder(UUID.randomUUID(), recentRate.getValue()));
    }
}
