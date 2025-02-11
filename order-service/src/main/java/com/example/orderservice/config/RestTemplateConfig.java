package com.example.orderservice.config;

import com.example.orderservice.interceptor.RestTemplateLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    /**
     * RestTemplate 빈 생성 시 요청/응답 로그를 기록하는 Interceptor 추가
     *
     * @param logInterceptor 요청 및 응답을 로깅하는 인터셉터
     * @return 로깅 인터셉터가 적용된 RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateLogInterceptor logInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(logInterceptor);
        return restTemplate;
    }
}
