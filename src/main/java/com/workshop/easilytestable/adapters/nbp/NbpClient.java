package com.workshop.easilytestable.adapters.nbp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.workshop.easilytestable.domain.gold.ExchangeRate;
import io.vavr.collection.List;
import lombok.NonNull;
import lombok.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "nbpClient", url = "${nbp.service.url}")
public interface NbpClient {
    @GetMapping("/cenyzlota")
    List<NbpExchangeRate> getRecentRate(@RequestParam String format);
}

@Value
class NbpExchangeRate {

    @JsonProperty("cena")
    @NonNull
    BigDecimal value;

    ExchangeRate toExchangeRate() {
        return new ExchangeRate(value);
    }
}
