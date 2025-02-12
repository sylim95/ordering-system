package com.example.productservice.app.domain.service;

import com.example.productservice.app.domain.model.Product;
import com.example.productservice.app.port.out.LoadProductPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {

    @Mock
    LoadProductPort loadProductPort;

    @InjectMocks
    private ProductQueryService productQueryService;

    private final List<Product> productList = new ArrayList<>();

    @BeforeEach
    void init() {
        productList.add(Product.builder()
                .id(100L)
                .name("테스트 상품1")
                .price(BigDecimal.valueOf(1000))
                .quantity(100)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .version(1)
                .build());

        productList.add(Product.builder()
                .id(101L)
                .name("테스트 상품2")
                .price(BigDecimal.valueOf(2000))
                .quantity(101)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .version(1)
                .build());

        productList.add(Product.builder()
                .id(102L)
                .name("테스트 상품3")
                .price(BigDecimal.valueOf(3000))
                .quantity(102)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .version(1)
                .build());

        productList.add(Product.builder()
                .id(103L)
                .name("테스트 상품4")
                .price(BigDecimal.valueOf(4000))
                .quantity(103)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .version(1)
                .build());
    }

    @Test
    void success_find_product_all() {
        when(loadProductPort.findAllProductInfo()).thenReturn(productList);

        var result = productQueryService.findAllProductInfo();
        assertEquals(productList.get(0).getId(), result.get(0).getId());
    }

    @Test
    void success_find_product_all_by_paging() {
        final int pageNumber = 1;
        final int pageSize = 2;

        var list = new ArrayList<Product>();
        list.add(productList.get(0));
        list.add(productList.get(1));

        var page = new PageImpl<>(list, PageRequest.of(pageNumber, pageSize), list.size());

        when(loadProductPort.findAllProductInfoByPaging(any(Pageable.class))).thenReturn(page);

        var result = productQueryService.findAllProductInfoByPaging(pageNumber, pageSize);
        assertEquals(page.getSize(), result.getSize());
    }

    @Test
    void success_find_product() {
        final Long id = 100L;
        var product = productList.stream()
                .filter(p -> id.equals(p.getId())).findFirst().orElseThrow(null);

        when(loadProductPort.findProductInfoById(100L)).thenReturn(product);

        var result = productQueryService.findProductInfoById(100L);
        assertEquals(product.getId(), result.getId());
    }

}
