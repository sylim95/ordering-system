package com.example.productservice.app.domain.service;

import com.example.productservice.app.domain.model.Product;
import com.example.productservice.app.port.in.ProductQueryUseCase;
import com.example.productservice.app.port.out.LoadProductPort;
import com.example.productservice.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductQueryService implements ProductQueryUseCase {

    private final LoadProductPort loadProductPort;

    @Override
    public List<Product> findAllProductInfo() {
        return loadProductPort.findAllProductInfo();
    }

    @Override
    public Page<Product> findAllProductInfoByPaging(Integer page, Integer pageSize) {
        // 제품 이름을 기준으로 오름차순 정렬
        var pageable = of(PageUtil.getPage(page) - 1,
                PageUtil.getPageSize(pageSize),
                by(Sort.Direction.ASC, "name"));

        return loadProductPort.findAllProductInfoByPaging(pageable);
    }

    @Override
    public Product findProductInfoById(Long id) {
        return loadProductPort.findProductInfoById(id);
    }
}
