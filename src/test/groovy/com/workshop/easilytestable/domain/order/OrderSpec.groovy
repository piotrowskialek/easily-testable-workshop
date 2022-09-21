package com.workshop.easilytestable.domain.order

import com.workshop.easilytestable.domain.gold.ExchangeRate
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant

import static io.vavr.API.None

class OrderSpec extends Specification {

    long mockTimeMillis = 1592506514035L;

    @Unroll
    def "should correctly multiply exchangeRate: #exchangeRate by amount: #amount when creating the order from the CreateOrderCommand"() {
        given:
        CreateOrderCommand orderCommand = new CreateOrderCommand(
            amount,
            new BigDecimal(123.45),
            Instant.ofEpochMilli(mockTimeMillis),
            None()
        )
        ExchangeRate domainExchangeRate = new ExchangeRate(exchangeRate)
        UUID mockId = UUID.fromString("144df29e-9dbf-4a4b-be65-e8fcf07a0625")
        when:
        Order order = Order.of(mockId, domainExchangeRate, orderCommand)
        then:
        result == order.value
        where:
        amount                 | exchangeRate           | result
        new BigDecimal("10.0") | new BigDecimal("10.0") | new BigDecimal("100.0")
        new BigDecimal("12.0") | new BigDecimal("1.0")  | new BigDecimal("12.0")
        new BigDecimal("14.0") | new BigDecimal("10.0") | new BigDecimal("140.0")
        new BigDecimal("16.0") | new BigDecimal("1.0")  | new BigDecimal("16.0")
        new BigDecimal("18.0") | new BigDecimal("10.0") | new BigDecimal("180.0")
        new BigDecimal("20.0") | new BigDecimal("1.0")  | new BigDecimal("20.0")
    }
}
