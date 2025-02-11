package com.example.orderservice.api.controller.command;

import com.example.common.api.ApiResponseBody;
import com.example.orderservice.api.dto.response.OrderDeleteResponse;
import com.example.orderservice.api.dto.response.OrderSaveResponse;
import com.example.orderservice.api.dto.response.OrderUpdateResponse;
import com.example.orderservice.api.dto.resquest.OrderDeleteRequest;
import com.example.orderservice.api.dto.resquest.OrderSaveRequest;
import com.example.orderservice.api.dto.resquest.OrderUpdateRequest;
import com.example.orderservice.app.port.in.OrderCommandUseCase;
import com.example.orderservice.app.port.in.OrderDeleteCommand;
import com.example.orderservice.app.port.in.OrderSaveCommand;
import com.example.orderservice.app.port.in.OrderUpdateCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@ApiResponseBody
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderCommandController {

    private final OrderCommandUseCase orderCommandUseCase;

    /**
     * 주문 정보 저장
     * @param request: 저장 요청 객체
     * @return OrderSaveResponse 저장한 주문 정보
     * */
    @PostMapping
    public OrderSaveResponse saveOrderInfo(@RequestBody @Valid OrderSaveRequest request) {
        return OrderSaveResponse.of(
                orderCommandUseCase.addOrder(OrderSaveCommand.of(request)));
    }

    /**
     * 주문 정보 수정
     * - 주문 상태 혹은 배송지 수정 가능
     * @param id: 주문 번호
     * @param request: 수정 요청 객체
     * @return OrderUpdateResponse 수정한 주문 정보
     * */
    @PatchMapping("/{id}")
    public OrderUpdateResponse updateOrderInfo(@PathVariable("id") String id,
                                               @RequestBody @Valid OrderUpdateRequest request) {
        return OrderUpdateResponse.of(
                orderCommandUseCase.updateOrder(OrderUpdateCommand.of(id, request)));
    }

    /**
     * 주문 정보 취소 또는 삭제(soft delete)
     * - 승인되지 않은 주문: 취소, 삭제 가능
     * - 승인된 주문: 취소, 삭제 불가능
     * - 취소된 주문: 삭제 가능
     * - 삭제된 주문: 취소, 삭제 불가능
     * @param id: 주문 번호
     * @param request: 수정 요청 객체
     * @return OrderUpdateResponse 수정한 주문 정보
     * */
    @DeleteMapping("/{id}")
    public OrderDeleteResponse deleteOrderInfo(@PathVariable("id") String id,
                                               @RequestBody @Valid OrderDeleteRequest request) {
        return OrderDeleteResponse.of(
                orderCommandUseCase.deleteOrder(OrderDeleteCommand.of(id, request)));
    }
}
