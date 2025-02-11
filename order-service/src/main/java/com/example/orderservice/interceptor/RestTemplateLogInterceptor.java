package com.example.orderservice.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RestTemplateLogInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // 요청 로깅
        logRequest(request, body);

        // 실행 후 응답 받아오기
        ClientHttpResponse response = execution.execute(request, body);

        // 응답 로깅
        logResponse(response);

        return response;
    }

    /**
     * restTemplate으로 호출하는 요청 로깅
     * @param request
     * @param body
     * */
    private void logRequest(HttpRequest request, byte[] body) {
        log.info("[Request] {} {}", request.getMethod(), request.getURI());
        if (body.length > 0) {
            log.info("Body: {}", new String(body, StandardCharsets.UTF_8));
        }
    }

    /**
     * restTemplate으로 호출하는 응답 로깅
     * @param response
     * */
    private void logResponse(ClientHttpResponse response) throws IOException {
        log.info("[Response] Status: {} {}", response.getStatusCode(), response.getStatusText());
    }
}
