package com.workshop.easilytestable

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.mongodb.DBObject
import com.workshop.easilytestable.util.KafkaConsumerProvider
import com.workshop.easilytestable.util.NbpMockConfigurer
import groovy.json.JsonSlurper
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext
@EmbeddedKafka
@ActiveProfiles("integration")
class CreateOrderSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private NbpMockConfigurer deviceMockConfigurer

    @Autowired
    private MongoTemplate mongoTemplate

    @Autowired
    private KafkaConsumerProvider consumerProvider

    @Rule
    private WireMockRule deviceMock = new WireMockRule(10101);

    Consumer<String, String> orderCreatedEventConsumer

    void setup() {
        orderCreatedEventConsumer = consumerProvider.configureConsumer();
        deviceMock.resetAll();
    }

    def "should return an UUID of a created order and save it to the mongodb and send a domain event to kafka"() {
        given:
            deviceMockConfigurer.mockNbpResponse(200, "[{\"data\":\"2020-06-19\",\"cena\":10.00}]", "json")

        when:
            ResultActions resultActions = mvc.perform(post("/orders")
                .content("{\"amount\": 123.45, \"taxRate\": 123.45, \"submissionDate\": \"1592506514035\", \"comment\" : \"xD\" }")
                .contentType(MediaType.APPLICATION_JSON))
        then:
            resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath('$.id').exists())
                .andReturn()

        when:
            List orders = mongoTemplate.findAll(DBObject.class, "orders")
        then:
            orders.head()

        when:
            ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(orderCreatedEventConsumer, "orderCreated")
            def parsedEvent = new JsonSlurper().parseText(singleRecord.value())
        then:
            parsedEvent
            parsedEvent.amount == 123.45
            parsedEvent.value == 1234.5
    }
}