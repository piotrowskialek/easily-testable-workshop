package com.workshop.easilytestable

import com.fasterxml.jackson.databind.ObjectMapper
import com.workshop.easilytestable.api.order.dto.CreateOrderRequest
import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions

import java.time.Instant

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CreateOrderSpec extends IntegrationSpec {

    @Autowired
    private MockMvc mvc

    @Autowired
    private ObjectMapper objectMapper

    def "should return an ID of a created order and save it to the DB and send a domain event"() {
        given: "a working nbp api"
            mockValidGoldPrice(200, goldExchangeRateResponse(new BigDecimal("10.00")))

        when: "create order request is send"
            ResultActions resultActions = mvc.perform(post("/orders")
                .content(createOrderRequest(new BigDecimal("123.45")))
                .contentType(MediaType.APPLICATION_JSON))

        then: "create order response is returned"
            resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath('$.orderId').exists())

        and: "order should be saved to the DB"
            List orders = findAllOrders()
            orders.head()

        and: "OrderCreatedEvent should be sent"
            def orderCreatedEvent = consumeOrderCreatedEvent()
            orderCreatedEvent
            orderCreatedEvent.amount == 123.45
            orderCreatedEvent.value == 1234.5
    }

    private String createOrderRequest(BigDecimal goldWeight) {
        return objectMapper.writeValueAsString(new CreateOrderRequest(
            goldWeight,
            new BigDecimal("123.45"),
            Instant.ofEpochMilli(1592506514035),
            Option.of("123.45 gold pls")
        ))
    }

    private String goldExchangeRateResponse(BigDecimal price) {
        return "[{\"data\":\"2020-06-19\",\"cena\":${price.toString()}}]".toString()
    }
}
