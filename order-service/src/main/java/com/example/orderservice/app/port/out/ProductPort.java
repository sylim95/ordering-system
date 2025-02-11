package com.example.orderservice.app.port.out;


import com.example.orderservice.app.port.out.dto.ProductDto;

import java.util.List;

public interface ProductPort {
    void fetchProducts(List<ProductDto> products);
    ProductDto getProductById(Long id);
}
