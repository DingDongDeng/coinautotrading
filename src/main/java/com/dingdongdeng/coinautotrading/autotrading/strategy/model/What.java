package com.dingdongdeng.coinautotrading.autotrading.strategy.model;

import com.dingdongdeng.coinautotrading.autotrading.type.OrderType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class What {

    private OrderType orderType;
}