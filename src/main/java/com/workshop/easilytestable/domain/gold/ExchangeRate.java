package com.workshop.easilytestable.domain.gold;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class ExchangeRate {
    @NonNull
    BigDecimal value;
}
