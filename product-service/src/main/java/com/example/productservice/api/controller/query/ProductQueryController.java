package com.example.productservice.api.controller.query;

import com.example.common.api.ApiResponseBody;
import com.example.productservice.api.dto.response.ProductQueryResponse;
import com.example.productservice.app.port.in.ProductQueryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@ApiResponseBody
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryUseCase productQueryUseCase;

    /**
     * 제품 전체 정보 조회
     * @return List<ProductQueryResponse> 제품 정보 리스트
     * */
    @GetMapping
    public List<ProductQueryResponse> findProductList() {
        return productQueryUseCase.findAllProductInfo()
                .stream().map(ProductQueryResponse::of).toList();
    }

    /**
     * 제품 정보 조회(페이징)
     * @return List<ProductQueryResponse> 제품 정보 리스트
     * */
    @GetMapping("/paging")
    public Page<ProductQueryResponse> findProductPageList(@RequestParam("page") Integer page,
                                                          @RequestParam("pageSize") Integer pageSize) {
        return productQueryUseCase.findAllProductInfoByPaging(page, pageSize)
                .map(ProductQueryResponse::of);
    }
}
