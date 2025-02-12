package com.example.productservice.app.domain.service;

import com.example.common.error.exception.BusinessException;
import com.example.productservice.app.domain.model.Product;
import com.example.productservice.app.domain.model.ProductHistory;
import com.example.productservice.app.port.in.ProductCommandUseCase;
import com.example.productservice.app.port.in.ProductSaveCommand;
import com.example.productservice.app.port.in.ProductUpdateCommand;
import com.example.productservice.app.port.out.LoadProductPort;
import com.example.productservice.app.port.out.SaveProductPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCommandService implements ProductCommandUseCase {

    private final LoadProductPort loadProductPort;

    private final SaveProductPort saveProductPort;

    @Override
    @Transactional
    public Product addProduct(ProductSaveCommand command) {
        return saveProductPort.save(Product.createProduct(command));
    }

    @Override
    @Transactional
    public Product updateProduct(ProductUpdateCommand command) {
        Product product = loadProductPort.findProductInfoById(command.getId());
        product.updateProduct(command);
        saveProductPort.save(product);

        return product;
    }


    /**
     * 제품의 재고를 업데이트
     *
     * 1. 요청된 ID 목록으로 제품 정보를 조회
     * 2. 각 제품에 대해 요청된 수량만큼 재고를 차감
     * 3. 낙관적 락(Optimistic Lock)을 적용하여 동시 업데이트 충돌 감지
     * 4. 500ms 간격으로 2회 재시도 요청
     *
     * 낙관적 락을 사용하면 트랜잭션이 커밋되는 시점에 버전 충돌이 감지되며 예외가 발생
     *
     * 충돌이 빈번한 경우 비관적 락(Pessimistic Lock) 또는
     * 트랜잭션 레벨 격상을 고려 - 다만 성능 저하의 우려가 있음
     *
     * @param commands
     * @throws BusinessException 동시 업데이트 충돌 발생 시 예외 발생
     */
    @Override
    @Transactional
    @Retryable(
        retryFor = ObjectOptimisticLockingFailureException.class,
        backoff = @Backoff(delay = 500)
    )
    public void updateProductStocks(List<ProductUpdateCommand> commands) {
        log.info("제품 재고 업데이트 시작...");
        Map<Long, Product> productMap = loadProductPort.findAllProductInfoByIds(
                commands.stream().map(ProductUpdateCommand::getId).toList()
        ).stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        for (ProductUpdateCommand productCommand : commands) {
            Product product = Optional.ofNullable(productMap.get(productCommand.getId()))
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productCommand.getId()));

            product.updateStock(productCommand.getQuantity());
        }

        saveProductPort.saveAll(new ArrayList<>(productMap.values()));
    }

    /**
     * 재시도를 모두 실패한 경우 실행
     * @param e - ObjectOptimisticLockingFailureException 예외
     * @param commands - 실패한 요청 목록
     * @exception BusinessException
     * */
    @Recover
    public void recover(ObjectOptimisticLockingFailureException e, List<ProductUpdateCommand> commands) {
        log.error("동시 업데이트 충돌 발생, 최대 재시도 횟수 초과 : {}", e.getMessage());
        throw new BusinessException("동시 업데이트 충돌 발생", e);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = loadProductPort.findProductInfoById(productId);
        // 제품 이력 추가
        saveProductPort.saveHistory(ProductHistory.createProductHistory(product));
        saveProductPort.delete(product);
    }
}
