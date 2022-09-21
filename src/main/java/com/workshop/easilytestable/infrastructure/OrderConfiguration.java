package com.workshop.easilytestable.infrastructure;

import com.workshop.easilytestable.adapters.mongo.MongoOrderRepository;
import com.workshop.easilytestable.adapters.mongo.MongoOrderStorage;
import com.workshop.easilytestable.adapters.nbp.NbpClient;
import com.workshop.easilytestable.adapters.nbp.NbpExchangeRateProvider;
import com.workshop.easilytestable.domain.gold.ExchangeRateProvider;
import com.workshop.easilytestable.domain.order.OrderCreatedEventSender;
import com.workshop.easilytestable.domain.order.OrderFacade;
import com.workshop.easilytestable.domain.order.OrderStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    OrderStorage mongoOrderStorage(MongoOrderRepository mongoOrderRepository) {
        return new MongoOrderStorage(mongoOrderRepository);
    }

    @Bean
    ExchangeRateProvider nbpExchangeRateProvider(NbpClient nbpClient) {
        return new NbpExchangeRateProvider(nbpClient);
    }

    @Bean
    OrderFacade orderFacade(ExchangeRateProvider nbpExchangeRateProvider,
                            OrderStorage mongoOrderStorage,
                            OrderCreatedEventSender kafkaOrderEventSender) {
        return new OrderFacade(nbpExchangeRateProvider, mongoOrderStorage, kafkaOrderEventSender);
    }
}
