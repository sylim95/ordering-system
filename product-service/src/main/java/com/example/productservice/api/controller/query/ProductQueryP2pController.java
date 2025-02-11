package com.example.productservice.api.controller.query;

import com.example.common.api.ApiResponseBody;
import com.example.productservice.api.dto.response.ProductQueryResponse;
import com.example.productservice.app.port.in.ProductQueryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Internal API
 * */
@Slf4j
@RestController
@ApiResponseBody
@RequestMapping("/api/int/product")
@RequiredArgsConstructor
public class ProductQueryP2pController {

    private final ProductQueryUseCase productQueryUseCase;

    /**
     * 제품 정보 단건 조회
     * @return ProductQueryResponse: 제품 정보
     * */
    @GetMapping("/{id}")
    public ProductQueryResponse findProductInfoById(@PathVariable("id") Long id) {
        return ProductQueryResponse.of(productQueryUseCase.findProductInfoById(id));
    }
}
