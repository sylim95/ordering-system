package com.example.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (String code, String message, T body) {

    public ApiResponse {
        Objects.requireNonNull(code, "code는 null일 수 없습니다.");
    }

    public ApiResponse(HttpStatus status, String message) {
        this(status, message, null);
    }

    public ApiResponse(HttpStatus status, String message, T body) {
        this(String.valueOf(status.value()), message, body);
    }
}
