package com.example.productservice.app.port.in;


import com.example.productservice.app.domain.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductQueryUseCase {
    List<Product> findAllProductInfo();
    Page<Product> findAllProductInfoByPaging(Integer page, Integer pageSize);
    Product findProductInfoById(Long id);
}
