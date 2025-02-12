package com.example.productservice.app.domain.service;

import com.example.common.error.exception.BusinessException;
import com.example.productservice.app.domain.model.Product;
import com.example.productservice.app.port.in.ProductUpdateCommand;
import com.example.productservice.app.port.out.LoadProductPort;
import com.example.productservice.app.port.out.SaveProductPort;
import com.example.productservice.infra.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableRetry
@ExtendWith(MockitoExtension.class)
class ProductCommandServiceTest {

    @MockitoBean
    private SaveProductPort saveProductPort;

    @MockitoBean
    private LoadProductPort loadProductPort;

    @Autowired
    private ProductCommandService productCommandService;


    @Test
    @Transactional
    void success_product_update_stocks() {
        List<ProductUpdateCommand> commands = List.of(
                ProductUpdateCommand.builder().id(1L).quantity(5).build(),
                ProductUpdateCommand.builder().id(2L).quantity(3).build()
        );

        Product product1 = Product.builder()
                .id(1L)
                .quantity(10)
                .version(1)
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .quantity(10)
                .version(1)
                .build();

        when(loadProductPort.findAllProductInfoByIds(any())).thenReturn(List.of(product1, product2));

        productCommandService.updateProductStocks(commands);

        assertEquals(5, product1.getQuantity());  // 10 - 5
        assertEquals(7, product2.getQuantity()); // 10 - 3

        verify(saveProductPort, times(1)).saveAll(any());
    }

    @Test
    void success_product_update_stocks_optimistic_lock() {
        List<ProductUpdateCommand> commands = List.of(
                ProductUpdateCommand.builder().id(1L).quantity(1).build()
        );

        Product product = Product.builder()
                .id(1L)
                .quantity(10)
                .version(1)
                .build();

        when(loadProductPort.findAllProductInfoByIds(any())).thenReturn(List.of(product));

        doThrow(new ObjectOptimisticLockingFailureException(ProductEntity.class, 1L))
            .doThrow(new ObjectOptimisticLockingFailureException(ProductEntity.class, 1L))
                .doNothing()
                    .when(saveProductPort).saveAll(any());

        productCommandService.updateProductStocks(commands);

        verify(saveProductPort, times(3)).saveAll(any());
    }

    @Test
    void fail_product_update_stocks_after_max_retry() {
        List<ProductUpdateCommand> commands = List.of(
                ProductUpdateCommand.builder().id(1L).quantity(1).build()
        );

        Product product = Product.builder()
                .id(1L)
                .quantity(10)
                .version(1)
                .build();

        when(loadProductPort.findAllProductInfoByIds(any())).thenReturn(List.of(product));

        doThrow(new ObjectOptimisticLockingFailureException(ProductEntity.class, 1L))
                .when(saveProductPort).saveAll(any());

        assertThrows(BusinessException.class, () -> productCommandService.updateProductStocks(commands));

        verify(saveProductPort, times(3)).saveAll(any());
    }

}
