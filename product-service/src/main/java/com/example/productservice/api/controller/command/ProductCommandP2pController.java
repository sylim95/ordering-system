package com.example.productservice.api.controller.command;


import com.example.common.api.ApiResponseBody;
import com.example.productservice.api.dto.request.ProductUpdateRequest;
import com.example.productservice.app.port.in.ProductCommandUseCase;
import com.example.productservice.app.port.in.ProductUpdateCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Internal API
 * */
@Slf4j
@RestController
@ApiResponseBody
@RequestMapping("/api/int/product")
@RequiredArgsConstructor
public class ProductCommandP2pController {

    private final ProductCommandUseCase productCommandUseCase;

    /**
     * 제품 재고 정보 수정
     * @param request: 수정할 제품 정보
     * */
    @PutMapping
    public void updateProductStocks(@RequestBody @Valid List<ProductUpdateRequest> request) {
        productCommandUseCase.updateProductStocks(
                request.stream().map(ProductUpdateCommand::of).toList());
    }
}
