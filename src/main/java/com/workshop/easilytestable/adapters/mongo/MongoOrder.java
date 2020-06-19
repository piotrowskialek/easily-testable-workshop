package com.workshop.easilytestable.adapters.mongo;

import com.workshop.easilytestable.domain.order.Order;
import com.workshop.easilytestable.domain.order.OrderStatus;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Document(collection = "orders")
@Value
class MongoOrder {

    @Id
    @NonNull
    UUID id;

    @NonNull
    BigDecimal amount;

    @NonNull
    BigDecimal value;

    @NonNull
    BigDecimal taxRate;

    long submissionDate;

    @NonNull
    Option<String> comment;

    @NonNull
    String status;

    Order toOrder() {
        return new Order(
                id,
                amount,
                value,
                taxRate,
                Instant.ofEpochMilli(submissionDate),
                comment,
                OrderStatus.valueOf(status)
        );
    }

    static MongoOrder of(Order order) {
        return new MongoOrder(
                order.getId(),
                order.getAmount(),
                order.getValue(),
                order.getTaxRate(),
                order.getSubmissionDate().toEpochMilli(),
                order.getComment(),
                order.getStatus().toString()
        );
    }
}