package com.workshop.easilytestable.adapters.mongo;

import io.vavr.collection.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MongoOrderRepository extends MongoRepository<MongoOrder, UUID> {
    List<MongoOrder> findAllBySubmissionDateBetween(long from, long to);
}
