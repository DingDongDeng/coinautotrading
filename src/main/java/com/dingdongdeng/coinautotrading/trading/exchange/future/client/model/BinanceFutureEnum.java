package com.dingdongdeng.coinautotrading.trading.exchange.future.client.model;

import com.dingdongdeng.coinautotrading.common.type.*;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class BinanceFutureEnum {

    @Getter
    @AllArgsConstructor
    public enum Type {
        LIMIT("지정가 주문", PriceType.LIMIT_PRICE),
        MARKET("시장가 주문", PriceType.SELLING_MARKET_PRICE),
        STOP("스탑 주문(지정가)", null),
        STOP_MARKET("스탑 주문(시장가)", null),
        TAKE_PROFIT("이익 실현 주문(시장가)", null),
        TAKE_PROFIT_MARKET("이익 실현 주문(시장가)", null),
        TRAILING_STOP_MARKET("콜백 주문", null),
        ;

        private String desc;
        private PriceType priceType;

        public static Type of(PriceType priceType) {
            return Arrays.stream(Type.values())
                    .filter(type -> type.getPriceType() == priceType)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(priceType.name()));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Side {
        BUY("매수", OrderType.BUY),
        SELL("매도", OrderType.SELL),
        ;

        private String desc;
        private OrderType orderType;

        public static Side of(OrderType orderType) {
            return Arrays.stream(Side.values())
                    .filter(type -> type.getOrderType() == orderType)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(orderType.name()));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum TimeInForce {
        GTC("Good Till Cancel", TimeInForceType.GTC),
        FOK("Fill Or Kill", TimeInForceType.FOK),
        GTX("Immediate or Cancel", TimeInForceType.GTX),
        IOC("Good Till Crossing", TimeInForceType.IOC),
        ;

        private String desc;
        private TimeInForceType timeInForceType;

        public static TimeInForce of(TimeInForceType timeInForceType) {
            return Arrays.stream(TimeInForce.values())
                    .filter(type -> type.getTimeInForceType() == timeInForceType)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(timeInForceType.name()));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Symbol {
        USDT_ADA("달러 에이다", "ADAUSDT", CoinType.ADA),
        USDT_SOL("달러 솔라나", "SOLUSDT", CoinType.SOLANA),
        USDT_XRP("달러 리플", "XRPUSDT", CoinType.XRP),
        USDT_ETH("달러 이더리움", "ETHUSDT", CoinType.ETHEREUM),
        USDT_AVAX("달러 아발란체", "AVAXUSDT", CoinType.AVALANCHE),
        USDT_DOT("달러 폴카닷", "DOTUSDT", CoinType.POLKADOT),
        USDT_DOGE("달러 도지", "DOGEUSDT", CoinType.DOGE),
        USDT_BTC("달러 비트코", "BTCUSDT", CoinType.BITCOIN),

        ;
        private String desc;
        private String code;
        private CoinType coinType;

        public static Symbol of(CoinType coinType) {
            return Arrays.stream(Symbol.values())
                .filter(type -> type.getCoinType() == coinType)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(coinType.name()));
        }

        public static Symbol of(String code) {
            return Arrays.stream(Symbol.values())
                .filter(type -> type.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(code));

        }
    }

    @Getter
    @AllArgsConstructor
    public enum Interval {
        MINUTE_1("1분봉", "1m", CandleUnit.UNIT_1M),
        MINUTE_3("3분봉", "3m", CandleUnit.UNIT_3M),
        MINUTE_5("5분봉", "5m", CandleUnit.UNIT_5M),
        MINUTE_10("10분봉", "10m", CandleUnit.UNIT_10M),
        MINUTE_15("15분봉", "15m", CandleUnit.UNIT_15M),
        MINUTE_30("30분봉", "30m", CandleUnit.UNIT_30M),
        MINUTE_60("60분봉", "60m", CandleUnit.UNIT_60M),
        DAY_1("1일봉", "1d", CandleUnit.UNIT_1D),
        MONTH_1("1달봉", "1M", CandleUnit.UNIT_1W),
        ;

        private String desc;
        private String code;
        private CandleUnit candleUnit;

        public static Interval of(CandleUnit candleUnit) {
            return Arrays.stream(Interval.values())
                .filter(type -> type.getCandleUnit() == candleUnit)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(candleUnit.name()));
        }
    }

    @Getter
    @AllArgsConstructor
    public enum PositionSide{

        LONG("롱 포지션", Position.LONG),
        SHORT("숏 포지션", Position.SHORT),
        ;

        private String desc;
        private Position position;

        public static PositionSide of(Position position){
            return Arrays.stream(PositionSide.values())
                    .filter(type -> type.getPosition() == position)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(position.name()));
        }

    }

}
