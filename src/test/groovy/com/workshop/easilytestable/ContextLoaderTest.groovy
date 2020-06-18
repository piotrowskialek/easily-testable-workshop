package com.workshop.easilytestable

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest
class ContextLoaderTest extends Specification {

    @Autowired
    private MockMvc mvc

    @Rule
    public WireMockRule deviceMock = new WireMockRule(10101);

    def "when context is loaded then all expected beans are created"() {
        expect: "no exception is thrown"
        mvc
    }
}