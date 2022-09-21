package com.workshop.easilytestable.api.order;

import com.workshop.easilytestable.api.order.dto.CreateOrderRequest;
import com.workshop.easilytestable.api.order.dto.CreateOrderResponse;
import com.workshop.easilytestable.domain.order.Order;
import com.workshop.easilytestable.domain.order.OrderFacade;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
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

    private final OrderFacade orderFacade;

    @ResponseStatus(CREATED)
    @PostMapping
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        UUID orderId = orderFacade.create(createOrderRequest.toDomain());
        return new CreateOrderResponse(orderId);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderFacade.getOrderById(UUID.fromString(id));
    }

    @GetMapping
    public List<Order> getOrdersByDateRange(@RequestParam long from,
                                            @RequestParam long to) {
        return orderFacade.getOrdersByDateRange(Instant.ofEpochMilli(from), Instant.ofEpochMilli(to));
    }
}

