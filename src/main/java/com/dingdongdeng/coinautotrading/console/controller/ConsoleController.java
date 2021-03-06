package com.dingdongdeng.coinautotrading.console.controller;

import com.dingdongdeng.coinautotrading.common.model.CommonResponse;
import com.dingdongdeng.coinautotrading.common.type.CoinExchangeType;
import com.dingdongdeng.coinautotrading.common.type.CoinType;
import com.dingdongdeng.coinautotrading.common.type.TradingTerm;
import com.dingdongdeng.coinautotrading.console.controller.model.TypeInfoResponse;
import com.dingdongdeng.coinautotrading.console.controller.model.TypeInfoResponse.TypeInfoTemplate;
import com.dingdongdeng.coinautotrading.trading.strategy.model.type.StrategyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConsoleController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/type")
    @ResponseBody
    public CommonResponse<TypeInfoResponse> getTypeInfo() {
        return CommonResponse.<TypeInfoResponse>builder()
            .body(
                TypeInfoResponse.builder()
                    .coinTypeList(TypeInfoTemplate.listOf(CoinType.toMap()))
                    .coinExchangeTypeList(TypeInfoTemplate.listOf(CoinExchangeType.toMap()))
                    .tradingTermList(TypeInfoTemplate.listOf(TradingTerm.toMap()))
                    .strategyCodeList(TypeInfoTemplate.listOf(StrategyCode.toMap()))
                    .build()
            )
            .message("get type info success")
            .build();
    }


}
