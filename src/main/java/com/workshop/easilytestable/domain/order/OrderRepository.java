package com.workshop.easilytestable.domain.order;

import io.vavr.collection.List;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> findById(UUID id);
    List<Order> findByDateRange(Instant from, Instant to);
    UUID insert(Order order);
}
