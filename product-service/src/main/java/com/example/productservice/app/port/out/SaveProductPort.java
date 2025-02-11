package com.example.productservice.app.port.out;


import com.example.productservice.app.domain.model.Product;
import com.example.productservice.app.domain.model.ProductHistory;

import java.util.List;

public interface SaveProductPort {

    Product save(Product product);
    void saveAll(List<Product> products);
    void delete(Product product);
    void saveHistory(ProductHistory product);
}
