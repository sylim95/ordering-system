package com.example.productservice.app.port.out;


import com.example.productservice.app.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoadProductPort {

    List<Product> findAllProductInfo();
    Page<Product> findAllProductInfoByPaging(Pageable pageable);
    Product findProductInfoById(Long productId);
    List<Product> findAllProductInfoByIds(List<Long> productIds);
}
