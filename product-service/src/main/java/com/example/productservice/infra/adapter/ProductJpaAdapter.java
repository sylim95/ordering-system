package com.example.productservice.infra.adapter;

import com.example.common.error.exception.BusinessException;
import com.example.productservice.app.domain.model.Product;
import com.example.productservice.app.domain.model.ProductHistory;
import com.example.productservice.app.port.out.LoadProductPort;
import com.example.productservice.app.port.out.SaveProductPort;
import com.example.productservice.infra.mapper.ProductHistoryMapper;
import com.example.productservice.infra.mapper.ProductMapper;
import com.example.productservice.infra.repository.ProductHistoryRepository;
import com.example.productservice.infra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductJpaAdapter implements LoadProductPort, SaveProductPort {

    private final ProductRepository productRepository;

    private final ProductHistoryRepository productHistoryRepository;

    @Override
    public List<Product> findAllProductInfo() {
        return ProductMapper.INSTANCE.toDomainList(productRepository.findAll());
    }

    @Override
    public Page<Product> findAllProductInfoByPaging(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper.INSTANCE::toDomain);
    }

    @Override
    public Product findProductInfoById(Long productId) {
        return ProductMapper.INSTANCE.toDomain(productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 정보입니다.")));
    }

    @Override
    public List<Product> findAllProductInfoByIds(List<Long> productIds) {
        return ProductMapper.INSTANCE.toDomainList(productRepository.findAllById(productIds));
    }

    @Override
    public Product save(Product product) {
        return ProductMapper.INSTANCE.toDomain(
                productRepository.save(ProductMapper.INSTANCE.toEntity(product)));
    }

    @Override
    public void saveAll(List<Product> products) {
        productRepository.saveAll(ProductMapper.INSTANCE.toEntityList(products));
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(ProductMapper.INSTANCE.toEntity(product));
    }

    @Override
    public void saveHistory(ProductHistory product) {
        productHistoryRepository.save(ProductHistoryMapper.INSTANCE.toEntity(product));
    }
}
