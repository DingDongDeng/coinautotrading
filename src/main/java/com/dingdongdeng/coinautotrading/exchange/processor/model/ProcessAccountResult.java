package com.dingdongdeng.coinautotrading.exchange.processor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class ProcessAccountResult {

    private String currency; // 화폐를 의미하는 영문 대문자 코드
    private Double balance; // 주문가능 금액/수량
    private Double locked; // 주문 중 묶여있는 금액/수량
    private Double avgBuyPrice; // 매수평균가
    private boolean avgBuyPriceModified; // 매수평균가 수정 여부
    private String unitCurrency; // 평단가 기준 화폐
}