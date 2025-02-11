package com.example.productservice.api.controller.command;

import com.example.common.api.ApiResponseBody;
import com.example.productservice.api.dto.request.ProductSaveRequest;
import com.example.productservice.api.dto.request.ProductUpdateRequest;
import com.example.productservice.api.dto.response.ProductSaveResponse;
import com.example.productservice.api.dto.response.ProductUpdateResponse;
import com.example.productservice.app.port.in.ProductCommandUseCase;
import com.example.productservice.app.port.in.ProductSaveCommand;
import com.example.productservice.app.port.in.ProductUpdateCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@ApiResponseBody
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductCommandController {

    private final ProductCommandUseCase productCommandUseCase;

    /**
     * 제품 정보 저장
     * @param request: 저장 요청 객체
     * @return ProductSaveResponse 저장한 제품 정보
     * */
    @PostMapping
    public ProductSaveResponse saveProductInfo(@RequestBody @Valid ProductSaveRequest request) {
        return ProductSaveResponse.of(
                productCommandUseCase.addProduct(ProductSaveCommand.of(request)));
    }

    /**
     * 제품 정보 수정
     * @param id: 제품 아이디
     * @param request: 수정 요청 사항
     * @return ProductUpdateResponse: 수정한 제품 정보
     * */
    @PatchMapping("/{id}")
    public ProductUpdateResponse updateProductInfo(@PathVariable("id") Long id,
                                                   @RequestBody @Valid ProductUpdateRequest request) {
        return ProductUpdateResponse.of(
                productCommandUseCase.updateProduct(
                        ProductUpdateCommand.of(ProductUpdateRequest.of(id, request))));
    }

    /**
     * 제품 정보 삭제
     * - hard_delete 후 제품 이력(product_history) 테이블로 이관
     * @param id: 제품 아이디
     * */
    @DeleteMapping("/{id}")
    public void deleteProductInfo(@PathVariable("id") Long id) {
        productCommandUseCase.deleteProduct(id);
    }
}
