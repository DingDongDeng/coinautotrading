package com.dingdongdeng.coinautotrading.trading.index.service;

import com.dingdongdeng.coinautotrading.exchange.service.model.ExchangeCandles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class IndexCalculator {

    public double getRsi(ExchangeCandles candles) {
        return 0d;
    }
}