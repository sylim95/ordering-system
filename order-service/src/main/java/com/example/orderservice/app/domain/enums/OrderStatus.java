package com.example.orderservice.app.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {
    PAID, APPROVED, CANCELLED, DELETED;

    public static OrderStatus ofCode(String code) {
        if (code == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(code))
                .findAny()
                .orElse(null);
    }
}