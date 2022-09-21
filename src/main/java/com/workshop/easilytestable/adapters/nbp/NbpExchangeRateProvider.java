package com.workshop.easilytestable.adapters.nbp;

import com.workshop.easilytestable.adapters.nbp.exception.GoldExchangeRateNotFound;
import com.workshop.easilytestable.domain.gold.ExchangeRate;
import com.workshop.easilytestable.domain.gold.ExchangeRateProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NbpExchangeRateProvider implements ExchangeRateProvider {

    private final NbpClient nbpClient;

    @Override
    public ExchangeRate getRecentRate() {
        return nbpClient.getRecentRate("json")
                .headOption()
                .map(NbpExchangeRate::toExchangeRate)
                .getOrElseThrow(GoldExchangeRateNotFound::new);
    }
}
