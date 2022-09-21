package com.workshop.easilytestable

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.workshop.easilytestable.util.KafkaAbility
import com.workshop.easilytestable.util.MongoAbility
import com.workshop.easilytestable.util.NbpAbility
import org.junit.Rule
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest
@EmbeddedKafka
@ActiveProfiles("integration")
abstract class IntegrationSpec extends Specification
    implements NbpAbility, KafkaAbility, MongoAbility {

    @Rule
    WireMockRule server = new WireMockRule(10101);

    void setup() {
        orderCreatedEventConsumer = configureConsumer()
        server.resetAll()
    }
}
