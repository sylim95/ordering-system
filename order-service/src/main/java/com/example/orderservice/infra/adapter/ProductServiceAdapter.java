package com.example.orderservice.infra.adapter;


import com.example.orderservice.app.port.out.ProductPort;
import com.example.orderservice.app.port.out.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductServiceAdapter implements ProductPort {

    private final RestTemplate restTemplate;
    private static final String PRODUCT_URL = "http://localhost:8184/api/int/product";

    /**
     * restTemplate 을 통한 제품 수정 호출
     * @param products
     * */
    @Override
    public void fetchProducts(List<ProductDto> products) {
        try {
            restTemplate.put(PRODUCT_URL, createHttpEntity(products));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new IllegalArgumentException(e.getMessage());
            }
            throw e;
        } catch (Exception e) {
            log.error("제품 수정 호출 중 에러 발생 : {} ", e.getMessage());
            throw e;
        }
    }

    /**
     * restTemplate 을 통한 제품 정보 조회
     * @param id
     * */
    @Override
    public ProductDto getProductById(Long id) {
        try {
            String url = UriComponentsBuilder.fromUriString(PRODUCT_URL)
                    .queryParam("id", id)
                    .toUriString();
            return restTemplate.getForObject(url, ProductDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new IllegalArgumentException(e.getMessage());
            }
            throw e;
        }
    }

    private HttpEntity<?> createHttpEntity(Object body) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

}
