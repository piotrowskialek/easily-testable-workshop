package com.workshop.easilytestable.util

import com.github.tomakehurst.wiremock.client.WireMock
import lombok.SneakyThrows
import org.springframework.stereotype.Component

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo

trait NbpAbility {

    @SneakyThrows
    void mockValidGoldPrice(int status, String body) {
        stubFor(
                WireMock
                        .get(urlEqualTo("/api/cenyzlota?format=json"))
                        .willReturn(aResponse()
                                .withStatus(status)
                                .withBody(body)
                                .withHeader("Content-Type", "application/json"))
        );
    }
}
