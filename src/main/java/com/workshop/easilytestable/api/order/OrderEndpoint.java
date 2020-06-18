package com.workshop.easilytestable.api.order;

import com.workshop.easilytestable.api.order.dto.CreateOrderRequest;
import com.workshop.easilytestable.domain.order.Order;
import com.workshop.easilytestable.domain.order.OrderFactory;
import com.workshop.easilytestable.domain.order.OrderProvider;
import io.vavr.collection.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
class OrderEndpoint {

    private final OrderFactory orderFactory;
    private final OrderProvider orderProvider;

    @ResponseStatus(CREATED)
    @PostMapping
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        UUID orderId = orderFactory.create(createOrderRequest);
        return new CreateOrderResponse(orderId);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderProvider.getOrderById(UUID.fromString(id));
    }

    @GetMapping
    public List<Order> getOrdersByDateRange(@RequestParam Instant from,
                                            @RequestParam Instant to) {
        return orderProvider.getOrdersByDateRange(from, to);
    }
}

@Value
class CreateOrderResponse {
    @NonNull
    UUID id;
}