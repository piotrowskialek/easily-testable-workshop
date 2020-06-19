package com.workshop.easilytestable.domain.order

import com.workshop.easilytestable.api.order.dto.CreateOrderRequest
import com.workshop.easilytestable.domain.gold.ExchangeRate
import com.workshop.easilytestable.domain.gold.ExchangeRateProvider
import spock.lang.Specification
import spock.lang.Subject

import java.time.Instant

import static io.vavr.API.None

class OrderFactorySpec extends Specification {

    OrderRepository orderRepository = Stub()
    ExchangeRateProvider exchangeRateProvider = Stub()
    OrderCreatedEventSender orderCreatedEventSender = Stub()
    long mockTimeMillis = 1592506514035L;

    @Subject
    OrderFactory orderFactory = new OrderFactory(orderRepository, exchangeRateProvider, orderCreatedEventSender)

    def "should return correct order id when creating the order from the CreateOrderRequest"() {
        given:
            CreateOrderRequest orderRequest = new CreateOrderRequest(
                    new BigDecimal(123.45),
                    new BigDecimal(123.45),
                    Instant.ofEpochMilli(mockTimeMillis),
                    None()
            )
            UUID mockId = UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625")
            exchangeRateProvider.getRecentRate() >> new ExchangeRate(new BigDecimal("10.00"))
            orderRepository.insert(_) >> mockId
        when:
            UUID id = orderFactory.create(orderRequest)
        then:
            id == mockId
    }

    def "should return order with correct amount when creating the order from the CreateOrderRequest"() {
        given:
            CreateOrderRequest orderRequest = new CreateOrderRequest(
                    amount,
                    new BigDecimal(123.45),
                    Instant.ofEpochMilli(mockTimeMillis),
                    None()
            )
            UUID mockId = UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625")
        when:
            Order order = orderFactory.toOrder(orderRequest, mockId, exchangeRate)
        then:
            result == order.value
        where:
            amount                 | exchangeRate           | result
            new BigDecimal("10.0") | new BigDecimal("10.0") | new BigDecimal("100.0")
            new BigDecimal("12.0") | new BigDecimal("1.0") | new BigDecimal("12.0")
            new BigDecimal("14.0") | new BigDecimal("10.0") | new BigDecimal("140.0")
            new BigDecimal("16.0") | new BigDecimal("1.0") | new BigDecimal("16.0")
            new BigDecimal("18.0") | new BigDecimal("10.0") | new BigDecimal("180.0")
            new BigDecimal("20.0") | new BigDecimal("1.0") | new BigDecimal("20.0")
    }
}