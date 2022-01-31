package com.dingdongdeng.coinautotrading.exchange.service.model;

import com.dingdongdeng.coinautotrading.common.type.OrderState;
import com.dingdongdeng.coinautotrading.common.type.OrderType;
import com.dingdongdeng.coinautotrading.common.type.PriceType;
import com.dingdongdeng.coinautotrading.exchange.client.model.UpbitResponse.TradeResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class ExchangeOrder {

    private String orderId; // 주문의 고유 아이디
    private OrderType orderType; // 주문 종류
    private PriceType priceType; // 주문 방식
    private Double price; // 주문 당시 화폐 가격
    private Double avgPrice; // 체결 가격의 평균가
    private OrderState orderState; // 주문 상태
    private String market; // 마켓의 유일키
    private String createdAt; // 주문 생성 시간
    private Double volume; // 사용자가 입력한 주문 양
    private Double remainingVolume; // 체결 후 남은 주문 양
    private Double reservedFee; // 수수료로 예약된 비용
    private Double remainingFee; // 남은 수수료
    private Double paidFee; // 사용된 수수료
    private Double locked; // 거래에 사용중인 비용
    private Double executedVolume; // 체결된 양
    private Long tradeCount; // 해당 주문에 걸린 체결 수
    private List<TradeResponse> tradeList; // 체결
}
