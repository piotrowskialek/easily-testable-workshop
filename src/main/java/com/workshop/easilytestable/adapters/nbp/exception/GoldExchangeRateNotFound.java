package com.workshop.easilytestable.adapters.nbp.exception;

public class GoldExchangeRateNotFound extends RuntimeException {
    public GoldExchangeRateNotFound() {
        super("Exchange rates not found in NBP API");
    }
}
