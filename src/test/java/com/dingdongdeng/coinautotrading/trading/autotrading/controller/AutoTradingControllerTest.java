package com.dingdongdeng.coinautotrading.trading.autotrading.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dingdongdeng.coinautotrading.common.type.CoinExchangeType;
import com.dingdongdeng.coinautotrading.common.type.CoinType;
import com.dingdongdeng.coinautotrading.common.type.TradingTerm;
import com.dingdongdeng.coinautotrading.doc.ApiDocumentUtils;
import com.dingdongdeng.coinautotrading.domain.entity.ExchangeKey;
import com.dingdongdeng.coinautotrading.domain.repository.ExchangeKeyRepository;
import com.dingdongdeng.coinautotrading.trading.autotrading.aggregation.AutoTradingAggregation;
import com.dingdongdeng.coinautotrading.trading.autotrading.model.AutoTradingRegisterRequest;
import com.dingdongdeng.coinautotrading.trading.autotrading.model.AutoTradingResponse;
import com.dingdongdeng.coinautotrading.trading.autotrading.model.type.AutoTradingProcessStatus;
import com.dingdongdeng.coinautotrading.trading.exchange.client.UpbitClient;
import com.dingdongdeng.coinautotrading.trading.strategy.model.type.StrategyCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class, MockitoExtension.class})
class AutoTradingControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private AutoTradingAggregation autoTradingAggregation;

    @Autowired
    private ExchangeKeyRepository exchangeKeyRepository;
    @Autowired
    private UpbitClient upbitClient;
    @Value("${upbit.client.accessKey}")
    private String accessKey;
    @Value("${upbit.client.secretKey}")
    private String secretKey;

    private String userId = "123456";
    private String keyPairId;


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();

        //fixme ?????? ????????? ???????????? ?????? ????????? ?????????
        String keyPairId = UUID.randomUUID().toString();
        exchangeKeyRepository.save(
            ExchangeKey.builder()
                .pairId(keyPairId)
                .coinExchangeType(CoinExchangeType.UPBIT)
                .name("ACCESS_KEY")
                .value(accessKey)
                .userId(userId)
                .build()
        );

        exchangeKeyRepository.save(
            ExchangeKey.builder()
                .pairId(keyPairId)
                .coinExchangeType(CoinExchangeType.UPBIT)
                .name("SECRET_KEY")
                .value(secretKey)
                .userId(userId)
                .build()
        );

        this.keyPairId = keyPairId;
    }

    @Test
    public void ?????????_????????????_?????????_??????_?????????() throws Exception {

        String autoTradingProcessorId = "abawefawef-awefawefawe-awefawefwaef";

        String title = "RSI 30?????? ??????";
        CoinType coinType = CoinType.ETHEREUM;
        CoinExchangeType coinExchangeType = CoinExchangeType.UPBIT;
        String strategyIdentifyCode = StrategyCode.SCALE_TRADING_RSI.name() + ":" + UUID.randomUUID().toString();

        Mockito.doReturn(
            List.of(
                AutoTradingResponse.builder()
                    .title(title)
                    .processorId(autoTradingProcessorId)
                    .processDuration(1000)
                    .processStatus(AutoTradingProcessStatus.RUNNING)
                    .userId(userId)
                    .strategyIdentifyCode(strategyIdentifyCode)
                    .coinType(coinType)
                    .coinExchangeType(coinExchangeType)
                    .build(),
                AutoTradingResponse.builder()
                    .title(title)
                    .processorId(autoTradingProcessorId)
                    .processDuration(1000)
                    .processStatus(AutoTradingProcessStatus.RUNNING)
                    .userId(userId)
                    .strategyIdentifyCode(strategyIdentifyCode)
                    .coinType(coinType)
                    .coinExchangeType(coinExchangeType)
                    .build()
            )
        )
            .when(autoTradingAggregation).getUserProcessorList(Mockito.any());

        MvcResult result = this.mockMvc.perform(
            RestDocumentationRequestBuilders.get("/user/{userId}/autotrading", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
        )
            .andExpect(status().isOk())
            .andDo(
                document("user/autotrading",
                    ApiDocumentUtils.getDocumentRequest(),
                    ApiDocumentUtils.getDocumentResponse(),
                    requestHeaders(
                        headerWithName("userId").description("?????????ID")
                    ),
                    pathParameters(
                        RequestDocumentation.parameterWithName("userId").description("?????????ID")
                    ),
                    responseFields(
                        fieldWithPath("body[]").type(JsonFieldType.ARRAY).description("?????????").optional(),
                        fieldWithPath("body[].title").type(JsonFieldType.STRING).description("???????????? ????????? ???????????? ??????"),
                        fieldWithPath("body[].processorId").type(JsonFieldType.STRING).description("???????????? ???????????? ID"),
                        fieldWithPath("body[].processDuration").type(JsonFieldType.NUMBER).description("???????????? ?????? ??????"),
                        fieldWithPath("body[].processStatus").type(JsonFieldType.STRING).description("???????????? ???????????? ??????"),
                        fieldWithPath("body[].userId").type(JsonFieldType.STRING).description("?????????ID"),
                        fieldWithPath("body[].strategyIdentifyCode").type(JsonFieldType.STRING).description("?????? ?????? ?????? (RSI)"),
                        fieldWithPath("body[].coinType").type(JsonFieldType.STRING).description("???????????? ??? ?????? ?????? (ETHEREUM, DOGE ...)"),
                        fieldWithPath("body[].coinExchangeType").type(JsonFieldType.STRING).description("??????????????? ????????? ????????? ??????(upbit)"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????????").optional()
                    )
                )
            )
            .andReturn();
    }

    @Test
    public void ????????????_??????_?????????() throws Exception {

        String processorId = "abawefawef-awefawefawe-awefawefwaef";

        String title = "RSI 30?????? ??????";
        CoinType coinType = CoinType.ETHEREUM;
        CoinExchangeType coinExchangeType = CoinExchangeType.UPBIT;
        String strategyIdentifyCode = StrategyCode.SCALE_TRADING_RSI.name() + ":" + UUID.randomUUID().toString();

        AutoTradingRegisterRequest request = AutoTradingRegisterRequest.builder()
            .title(title)
            .coinType(CoinType.ETHEREUM)
            .coinExchangeType(CoinExchangeType.UPBIT)
            .tradingTerm(TradingTerm.SCALPING)
            .strategyCode(StrategyCode.SCALE_TRADING_RSI)
            .keyPairId(keyPairId)
            .build();

        Mockito.doReturn(
            AutoTradingResponse.builder()
                .title(title)
                .processorId(processorId)
                .processDuration(1000)
                .processStatus(AutoTradingProcessStatus.INIT)
                .userId(userId)
                .strategyIdentifyCode(strategyIdentifyCode)
                .coinType(coinType)
                .coinExchangeType(coinExchangeType)
                .build()
        )
            .when(autoTradingAggregation).register(Mockito.any(), Mockito.any());

        MvcResult result = this.mockMvc.perform(
            RestDocumentationRequestBuilders.post("/autotrading/register")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk())
            .andDo(
                document("autotrading/register",
                    ApiDocumentUtils.getDocumentRequest(),
                    ApiDocumentUtils.getDocumentResponse(),
                    requestHeaders(
                        headerWithName("userId").description("?????????ID")
                    ),
                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("???????????? ????????? ???????????? ??????"),
                        fieldWithPath("coinType").type(JsonFieldType.STRING).description("???????????? ??? ?????? ?????? (ETHEREUM, DOGE ...)"),
                        fieldWithPath("coinExchangeType").type(JsonFieldType.STRING).description("??????????????? ????????? ????????? ??????(upbit)"),
                        fieldWithPath("tradingTerm").type(JsonFieldType.STRING).description("??????????????? ???????????? (EXTREME_SCALPING, SCALPING,DAY,SWING)"),
                        fieldWithPath("strategyCode").type(JsonFieldType.STRING).description("?????? ?????? ?????? (RSI)"),
                        fieldWithPath("keyPairId").type(JsonFieldType.STRING).description("??????????????? ????????? ????????? ????????? ID")
                    ),
                    responseFields(
                        fieldWithPath("body").type(JsonFieldType.OBJECT).description("?????????").optional(),
                        fieldWithPath("body.title").type(JsonFieldType.STRING).description("???????????? ????????? ???????????? ??????"),
                        fieldWithPath("body.processorId").type(JsonFieldType.STRING).description("???????????? ???????????? ID"),
                        fieldWithPath("body.processDuration").type(JsonFieldType.NUMBER).description("???????????? ?????? ??????"),
                        fieldWithPath("body.processStatus").type(JsonFieldType.STRING).description("???????????? ???????????? ??????"),
                        fieldWithPath("body.userId").type(JsonFieldType.STRING).description("?????????ID"),
                        fieldWithPath("body.strategyIdentifyCode").type(JsonFieldType.STRING).description("?????? ?????? ?????? (RSI)"),
                        fieldWithPath("body.coinType").type(JsonFieldType.STRING).description("???????????? ??? ?????? ?????? (ETHEREUM, DOGE ...)"),
                        fieldWithPath("body.coinExchangeType").type(JsonFieldType.STRING).description("??????????????? ????????? ????????? ??????(upbit)"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????????").optional()
                    )
                )
            )
            .andReturn();
    }

    @Test
    public void ????????????_??????_?????????() throws Exception {

        String autoTradingProcessorId = "abawefawef-awefawefawe-awefawefwaef";

        String title = "RSI 30?????? ??????";
        CoinType coinType = CoinType.ETHEREUM;
        CoinExchangeType coinExchangeType = CoinExchangeType.UPBIT;
        String strategyIdentifyCode = StrategyCode.SCALE_TRADING_RSI.name() + ":" + UUID.randomUUID().toString();

        Mockito.doReturn(
            AutoTradingResponse.builder()
                .title(title)
                .processorId(autoTradingProcessorId)
                .processDuration(1000)
                .processStatus(AutoTradingProcessStatus.RUNNING)
                .userId(userId)
                .strategyIdentifyCode(strategyIdentifyCode)
                .coinType(coinType)
                .coinExchangeType(coinExchangeType)
                .build()
        )
            .when(autoTradingAggregation).start(Mockito.any(), Mockito.any());

        MvcResult result = this.mockMvc.perform(
            RestDocumentationRequestBuilders.post("/autotrading/{autoTradingProcessorId}/start", autoTradingProcessorId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
        )
            .andExpect(status().isOk())
            .andDo(
                document("autotrading/start",
                    ApiDocumentUtils.getDocumentRequest(),
                    ApiDocumentUtils.getDocumentResponse(),
                    requestHeaders(
                        headerWithName("userId").description("?????????ID")
                    ),
                    pathParameters(
                        RequestDocumentation.parameterWithName("autoTradingProcessorId").description("???????????? ???????????? ID")
                    ),
                    responseFields(
                        fieldWithPath("body").type(JsonFieldType.OBJECT).description("?????????").optional(),
                        fieldWithPath("body.title").type(JsonFieldType.STRING).description("???????????? ????????? ???????????? ??????"),
                        fieldWithPath("body.processorId").type(JsonFieldType.STRING).description("???????????? ???????????? ID"),
                        fieldWithPath("body.processDuration").type(JsonFieldType.NUMBER).description("???????????? ?????? ??????"),
                        fieldWithPath("body.processStatus").type(JsonFieldType.STRING).description("???????????? ???????????? ??????"),
                        fieldWithPath("body.userId").type(JsonFieldType.STRING).description("?????????ID"),
                        fieldWithPath("body.strategyIdentifyCode").type(JsonFieldType.STRING).description("?????? ?????? ?????? (RSI)"),
                        fieldWithPath("body.coinType").type(JsonFieldType.STRING).description("???????????? ??? ?????? ?????? (ETHEREUM, DOGE ...)"),
                        fieldWithPath("body.coinExchangeType").type(JsonFieldType.STRING).description("??????????????? ????????? ????????? ??????(upbit)"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????????").optional()
                    )
                )
            )
            .andReturn();
    }

    @Test
    public void ????????????_??????_?????????() throws Exception {
        String autoTradingProcessorId = "abawefawef-awefawefawe-awefawefwaef";

        String title = "RSI 30?????? ??????";
        CoinType coinType = CoinType.ETHEREUM;
        CoinExchangeType coinExchangeType = CoinExchangeType.UPBIT;
        String strategyIdentifyCode = StrategyCode.SCALE_TRADING_RSI.name() + ":" + UUID.randomUUID().toString();

        Mockito.doReturn(
            AutoTradingResponse.builder()
                .title(title)
                .processorId(autoTradingProcessorId)
                .processDuration(1000)
                .processStatus(AutoTradingProcessStatus.STOPPED)
                .userId(userId)
                .strategyIdentifyCode(strategyIdentifyCode)
                .coinType(coinType)
                .coinExchangeType(coinExchangeType)
                .build()
        )
            .when(autoTradingAggregation).stop(Mockito.any(), Mockito.any());

        MvcResult result = this.mockMvc.perform(
            RestDocumentationRequestBuilders.post("/autotrading/{autoTradingProcessorId}/stop", autoTradingProcessorId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
        )
            .andExpect(status().isOk())
            .andDo(
                document("autotrading/stop",
                    ApiDocumentUtils.getDocumentRequest(),
                    ApiDocumentUtils.getDocumentResponse(),
                    requestHeaders(
                        headerWithName("userId").description("?????????ID")
                    ),
                    pathParameters(
                        RequestDocumentation.parameterWithName("autoTradingProcessorId").description("???????????? ???????????? ID")
                    ),
                    responseFields(
                        fieldWithPath("body").type(JsonFieldType.OBJECT).description("?????????").optional(),
                        fieldWithPath("body.title").type(JsonFieldType.STRING).description("???????????? ????????? ???????????? ??????"),
                        fieldWithPath("body.processorId").type(JsonFieldType.STRING).description("???????????? ???????????? ID"),
                        fieldWithPath("body.processDuration").type(JsonFieldType.NUMBER).description("???????????? ?????? ??????"),
                        fieldWithPath("body.processStatus").type(JsonFieldType.STRING).description("???????????? ???????????? ??????"),
                        fieldWithPath("body.userId").type(JsonFieldType.STRING).description("?????????ID"),
                        fieldWithPath("body.strategyIdentifyCode").type(JsonFieldType.STRING).description("?????? ?????? ?????? (RSI)"),
                        fieldWithPath("body.coinType").type(JsonFieldType.STRING).description("???????????? ??? ?????? ?????? (ETHEREUM, DOGE ...)"),
                        fieldWithPath("body.coinExchangeType").type(JsonFieldType.STRING).description("??????????????? ????????? ????????? ??????(upbit)"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????????").optional()
                    )
                )
            )
            .andReturn();
    }

    @Test
    public void ????????????_??????_?????????() throws Exception {
        String autoTradingProcessorId = "abawefawef-awefawefawe-awefawefwaef";

        String title = "RSI 30?????? ??????";
        CoinType coinType = CoinType.ETHEREUM;
        CoinExchangeType coinExchangeType = CoinExchangeType.UPBIT;
        String strategyIdentifyCode = StrategyCode.SCALE_TRADING_RSI.name() + ":" + UUID.randomUUID().toString();

        Mockito.doReturn(
            AutoTradingResponse.builder()
                .title(title)
                .processorId(autoTradingProcessorId)
                .processDuration(1000)
                .processStatus(AutoTradingProcessStatus.TERMINATED)
                .userId(userId)
                .strategyIdentifyCode(strategyIdentifyCode)
                .coinType(coinType)
                .coinExchangeType(coinExchangeType)
                .build()
        )
            .when(autoTradingAggregation).terminate(Mockito.any(), Mockito.any());

        MvcResult result = this.mockMvc.perform(
            RestDocumentationRequestBuilders.post("/autotrading/{autoTradingProcessorId}/terminate", autoTradingProcessorId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
        )
            .andExpect(status().isOk())
            .andDo(
                document("autotrading/terminate",
                    ApiDocumentUtils.getDocumentRequest(),
                    ApiDocumentUtils.getDocumentResponse(),
                    requestHeaders(
                        headerWithName("userId").description("?????????ID")
                    ),
                    pathParameters(
                        RequestDocumentation.parameterWithName("autoTradingProcessorId").description("???????????? ???????????? ID")
                    ),
                    responseFields(
                        fieldWithPath("body").type(JsonFieldType.OBJECT).description("?????????").optional(),
                        fieldWithPath("body.title").type(JsonFieldType.STRING).description("???????????? ????????? ???????????? ??????"),
                        fieldWithPath("body.processorId").type(JsonFieldType.STRING).description("???????????? ???????????? ID"),
                        fieldWithPath("body.processDuration").type(JsonFieldType.NUMBER).description("???????????? ?????? ??????"),
                        fieldWithPath("body.processStatus").type(JsonFieldType.STRING).description("???????????? ???????????? ??????"),
                        fieldWithPath("body.userId").type(JsonFieldType.STRING).description("?????????ID"),
                        fieldWithPath("body.strategyIdentifyCode").type(JsonFieldType.STRING).description("?????? ?????? ?????? (RSI)"),
                        fieldWithPath("body.coinType").type(JsonFieldType.STRING).description("???????????? ??? ?????? ?????? (ETHEREUM, DOGE ...)"),
                        fieldWithPath("body.coinExchangeType").type(JsonFieldType.STRING).description("??????????????? ????????? ????????? ??????(upbit)"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????????").optional()
                    )
                )
            )
            .andReturn();
    }
}