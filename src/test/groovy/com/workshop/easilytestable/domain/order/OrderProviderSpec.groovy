package com.workshop.easilytestable.domain.order

import spock.lang.Specification
import spock.lang.Subject
import io.vavr.collection.List

import java.time.Instant

import static io.vavr.API.List
import static io.vavr.API.Some

class OrderProviderSpec extends Specification {

    OrderRepository orderRepository = Stub()
    long mockTimeMillis = 1592506514035L;

    @Subject
    OrderProvider orderProvider = new OrderProvider(orderRepository);

    def "should throw an OrderNotFoundException when order not found in the repository"() {
        given:
            orderRepository.findById(_) >> Optional.empty()
        when:
            orderProvider.getOrderById(UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625"))
        then:
            thrown(OrderNotFoundException)
    }

    def "should return an order when found in the repository"() {
        given:
            Order mockOrder = new Order(
                    UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625"),
                    new BigDecimal(123.45),
                    new BigDecimal(123.45),
                    new BigDecimal(123.45),
                    Instant.ofEpochMilli(mockTimeMillis),
                    Some("123"),
                    OrderStatus.ACCEPTED
            )
            orderRepository.findById(UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625")) >> Optional.of(mockOrder)
        when:
            Order resultOrder = orderProvider.getOrderById(UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625"))
        then:
            resultOrder == mockOrder
    }

    def "should return an empty list when no orders found in the repository for given period"() {
        given:
            orderRepository.findByDateRange(_, _) >> List()
        when:
            List<Order> orders = orderProvider.getOrdersByDateRange(Instant.MIN, Instant.MAX)
        then:
            orders.isEmpty()
    }

    def "should return a list with correct orders when mock order found in the repository for given period"() {
        given:
            orderRepository.findByDateRange(Instant.MIN, Instant.MAX) >> List(
                    new Order(
                    UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625"),
                    new BigDecimal(123.45),
                    new BigDecimal(123.45),
                    new BigDecimal(123.45),
                    Instant.ofEpochMilli(mockTimeMillis),
                    Some("123"),
                    OrderStatus.ACCEPTED
                    )
            )
        when:
            List<Order> orders = orderProvider.getOrdersByDateRange(Instant.MIN, Instant.MAX)
        then:
            orders.size() == 1
        and:
            orders.head().id == UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625")
    }
}
