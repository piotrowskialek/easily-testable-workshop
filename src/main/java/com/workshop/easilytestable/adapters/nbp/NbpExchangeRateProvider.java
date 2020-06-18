package com.workshop.easilytestable.adapters.nbp;

import com.workshop.easilytestable.domain.gold.ExchangeRate;
import com.workshop.easilytestable.domain.gold.ExchangeRateProvider;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NbpExchangeRateProvider implements ExchangeRateProvider {

    private final NbpClient nbpClient;

    @Override
    public ExchangeRate getRecentRate() {
        return nbpClient.getRecentRate("json").map(NbpExchangeRate::toExchangeRate).getOrElseThrow(GoldExchangeRateNotFound::new);
    }
}

@FeignClient(name = "nbpClient", url = "${nbp.service.url}")
interface NbpClient {
    @GetMapping("/cenyzlota")
    Option<NbpExchangeRate> getRecentRate(@RequestParam String format);
}

@Value
class NbpExchangeRate {

    @NonNull
    Instant date;

    @NonNull
    BigDecimal value;

    ExchangeRate toExchangeRate() {
        return new ExchangeRate(value);
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class GoldExchangeRateNotFound extends RuntimeException {}