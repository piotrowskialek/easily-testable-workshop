package com.workshop.easilytestable.adapters.mongo;

import com.workshop.easilytestable.domain.order.Order;
import com.workshop.easilytestable.domain.order.OrderRepository;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class MongoOrderRepository implements OrderRepository {

    MongoOrderClient mongoOrderClient;

    @Override
    public Optional<Order> findById(UUID id) {
        return mongoOrderClient
                .findById(id)
                .map(MongoOrder::toOrder);
    }

    @Override
    public List<Order> findByDateRange(Instant from, Instant to) {
        return List.ofAll(mongoOrderClient.findAllBySubmissionDateBetween(from.toEpochMilli(), to.toEpochMilli())).map(MongoOrder::toOrder);
    }

    @Override
    public UUID insert(Order order) {
        return mongoOrderClient.insert(MongoOrder.of(order)).getId();
    }
}

@Repository
interface MongoOrderClient extends MongoRepository<MongoOrder, UUID> {
    List<MongoOrder> findAllBySubmissionDateBetween(long from, long to);
}