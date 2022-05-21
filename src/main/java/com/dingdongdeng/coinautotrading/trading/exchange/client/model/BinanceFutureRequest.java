package com.dingdongdeng.coinautotrading.trading.exchange.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class BinanceFutureRequest {

    @ToString
    @Getter
    @Setter
    @Builder
    public static class FuturesAccountBalanceRequest {

        @JsonProperty("timestamp")
        private Long timestamp;
        @JsonProperty("signature")
        private String signature;
    }

}