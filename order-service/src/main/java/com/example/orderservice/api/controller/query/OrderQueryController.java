package com.example.orderservice.api.controller.query;

import com.example.common.api.ApiResponseBody;
import com.example.orderservice.api.dto.response.OrderQueryResponse;
import com.example.orderservice.api.dto.resquest.OrderSearchRequest;
import com.example.orderservice.app.port.in.OrderQueryUseCase;
import com.example.orderservice.app.port.in.OrderSearchCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@ApiResponseBody
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderQueryController {

    private final OrderQueryUseCase orderQueryUseCase;

    /**
     * 주문 전체 정보 조회
     * - 검색값: 상태, 시작일, 종료일
     * - 검색값이 없을 경우 모든 주문 정보 조회
     * @param request Optional: 조회 조건을 담은 요청 객체
     * @return List<OrderQueryResponse> 주문 정보 리스트
     * */
    @GetMapping
    public List<OrderQueryResponse> findOrderList(@ModelAttribute @Valid OrderSearchRequest request) {
        return orderQueryUseCase.findAllOrderInfo(OrderSearchCommand.of(request))
                .stream().map(OrderQueryResponse::of).toList();
    }

    /**
     * 고객 아이디 기준 주문 정보 조회
     * - 검색값: 상태, 시작일, 종료일
     * - 검색값이 없을 경우 모든 고객 주문 정보 조회
     * @param request Optional: 조회 조건을 담은 요청 객체
     * @return List<OrderQueryResponse> 주문 정보 리스트
     * */
    @GetMapping("/{customerId}")
    public List<OrderQueryResponse> findOrderListByCustomer(
            @PathVariable("customerId") Long customerId,
            @ModelAttribute @Valid OrderSearchRequest request) {
        return orderQueryUseCase.findAllOrderInfoByCustomerId(customerId, OrderSearchCommand.of(request))
                .stream().map(OrderQueryResponse::of).toList();
    }
}
