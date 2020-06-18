package com.workshop.easilytestable.domain.order;

import io.vavr.collection.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderProvider {

    private final OrderRepository orderRepository;

    public Order getOrderById(@NonNull UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }

    public List<Order> getOrdersByDateRange(@NonNull Instant from, @NonNull Instant to) {
        return orderRepository.findByDateRange(from, to);
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class OrderNotFoundException extends RuntimeException {}
