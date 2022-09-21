package com.workshop.easilytestable.adapters.mongo;

import com.workshop.easilytestable.domain.order.Order;
import com.workshop.easilytestable.domain.order.OrderStorage;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MongoOrderStorage implements OrderStorage {

    private final MongoOrderRepository mongoOrderRepository;

    @Override
    public Optional<Order> findById(UUID id) {
        return mongoOrderRepository
                .findById(id)
                .map(MongoOrder::toOrder);
    }

    @Override
    public List<Order> findByDateRange(Instant from, Instant to) {
        return List.ofAll(mongoOrderRepository.findAllBySubmissionDateBetween(from.toEpochMilli(), to.toEpochMilli())).map(MongoOrder::toOrder);
    }

    @Override
    public UUID insert(Order order) {
        return mongoOrderRepository.insert(MongoOrder.of(order)).getId();
    }
}
