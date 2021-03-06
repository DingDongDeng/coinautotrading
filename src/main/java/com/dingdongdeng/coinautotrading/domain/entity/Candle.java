package com.dingdongdeng.coinautotrading.domain.entity;

import com.dingdongdeng.coinautotrading.common.type.CandleUnit;
import com.dingdongdeng.coinautotrading.common.type.CoinExchangeType;
import com.dingdongdeng.coinautotrading.common.type.CoinType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "candle")
public class Candle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candle_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "coin_exchange_type")
    private CoinExchangeType coinExchangeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "coin_type")
    private CoinType coinType;

    @Enumerated(EnumType.STRING)
    @Column(name = "candle_unit")
    private CandleUnit candleUnit;

    @Column(name = "candle_date_time_utc")
    private LocalDateTime candleDateTimeUtc;

    @Column(name = "candle_date_time_kst")
    private LocalDateTime candleDateTimeKst;

    @Column(name = "opening_price")
    private Double openingPrice; // 시가

    @Column(name = "high_price")
    private Double highPrice; // 고가

    @Column(name = "low_price")
    private Double lowPrice; // 저가

    @Column(name = "trade_price")
    private Double tradePrice; // 종가

    @Column(name = "candle_acc_trade_price")
    private Double candleAccTradePrice; // 누적 거래 금액

    @Column(name = "candle_acc_trade_volume")
    private Double candleAccTradeVolume; // 누적 거래량
}
