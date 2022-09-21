package com.workshop.easilytestable.domain.order


import com.workshop.easilytestable.domain.gold.ExchangeRate
import com.workshop.easilytestable.domain.gold.ExchangeRateProvider
import com.workshop.easilytestable.domain.order.exception.OrderNotFoundException
import io.vavr.collection.List
import spock.lang.Specification
import spock.lang.Subject

import java.time.Instant

import static io.vavr.API.List
import static io.vavr.API.None
import static io.vavr.API.Some

class OrderFacadeSpec extends Specification {

    OrderStorage orderRepository = Stub()
    ExchangeRateProvider exchangeRateProvider = Stub()
    OrderCreatedEventSender orderCreatedEventSender = Stub()
    long mockTimeMillis = 1592506514035L;

    @Subject
    OrderFacade orderFacade = new OrderFacade(exchangeRateProvider, orderRepository, orderCreatedEventSender)

    def "should return correct order id when creating the order from the CreateOrderCommand"() {
        given:
            CreateOrderCommand orderRequest = new CreateOrderCommand(
                    new BigDecimal(123.45),
                    new BigDecimal(123.45),
                    Instant.ofEpochMilli(mockTimeMillis),
                    None()
            )
            UUID mockId = UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625")
            exchangeRateProvider.getRecentRate() >> new ExchangeRate(new BigDecimal("10.00"))
            orderRepository.insert(_ as Order) >> mockId
        when:
            UUID id = orderFacade.create(orderRequest)
        then:
            id == mockId
    }

    def "should throw an OrderNotFoundException when order not found in the repository"() {
        given:
        orderRepository.findById(_ as UUID) >> Optional.empty()
        when:
        orderFacade.getOrderById(UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625"))
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
        Order resultOrder = orderFacade.getOrderById(UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625"))
        then:
        resultOrder == mockOrder
    }

    def "should return an empty list when no orders found in the repository for given period"() {
        given:
        orderRepository.findByDateRange(_ as Instant, _ as Instant) >> List()
        when:
        List<Order> orders = orderFacade.getOrdersByDateRange(Instant.MIN, Instant.MAX)
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
        List<Order> orders = orderFacade.getOrdersByDateRange(Instant.MIN, Instant.MAX)
        then:
        orders.size() == 1
        and:
        orders.head().id == UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625")
    }
}
