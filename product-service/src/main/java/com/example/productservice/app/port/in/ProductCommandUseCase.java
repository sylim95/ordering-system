package com.example.productservice.app.port.in;


import com.example.productservice.app.domain.model.Product;

import java.util.List;

public interface ProductCommandUseCase {
    Product addProduct(ProductSaveCommand command);
    Product updateProduct(ProductUpdateCommand command);
    void updateProductStocks(List<ProductUpdateCommand> commands);
    void deleteProduct(Long productId);
}
