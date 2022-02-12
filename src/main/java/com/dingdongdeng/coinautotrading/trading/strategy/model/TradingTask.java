package com.dingdongdeng.coinautotrading.trading.strategy.model;

import com.dingdongdeng.coinautotrading.common.type.CoinType;
import com.dingdongdeng.coinautotrading.common.type.OrderType;
import com.dingdongdeng.coinautotrading.common.type.PriceType;
import com.dingdongdeng.coinautotrading.trading.strategy.model.type.TradingTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("tradingTask")
public class TradingTask {

    @Id
    private String id;
    private CoinType coinType;
    private OrderType orderType;
    private Double volume;
    private Double price;
    private PriceType priceType;

    // 주문 취소
    private String orderId;

    private TradingTag tag; // 주문의 의도를 구분하기 위한 값(ex: 손절 주문, 익절 주문, 매수 주문 등)

}
