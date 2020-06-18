package com.workshop.easilytestable

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.mongodb.DBObject
import com.workshop.easilytestable.util.NbpMockConfigurer
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
@DataMongoTest
class CreateOrderSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @Autowired
    private NbpMockConfigurer deviceMockConfigurer

    @Autowired
    private MongoTemplate mongoTemplate

    @Rule
    private WireMockRule deviceMock = new WireMockRule(10101);

    void setup() {
        deviceMock.resetAll();
    }

    def "should return an UUID of a created order and save it to the mongodb and send a domain event to kafka"() {
        given:
            deviceMockConfigurer.mockNbpResponse(200, "", "json")
        when:
        ResultActions resultActions = mvc.perform(post("/orders")
                .content(???)
                .contentType(MediaType.APPLICATION_JSON))
        then:
            resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath('$.url').value(???))
                .andReturn()
        and:
            List orders = mongoTemplate.findAll(DBObject.class, "orders")
            orders.head()
        and:
            //kafka sent
    }
}