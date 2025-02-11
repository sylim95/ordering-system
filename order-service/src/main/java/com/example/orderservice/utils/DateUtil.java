package com.example.orderservice.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class DateUtil {

    /**
     * 주어진 날짜의 시작 시간을 `LocalDateTime`으로 변환
     * 예: `2024-01-01` → `2024-01-01T00:00:00`
     * @param date 변환할 `LocalDate` 값
     * @return 해당 날짜의 시작 (`00:00:00`)을 포함한 `LocalDateTime`
     */
    public static LocalDateTime atStartDateTime(LocalDate date) {
        if(date == null) {
            return null;
        }

        return date.atStartOfDay();
    }

    /**
     * 주어진 날짜의 종료 시간을 `LocalDateTime`으로 변환
     * 예: `2024-01-01` → `2024-01-01T23:59:59`
     * @param date 변환할 `LocalDate` 값
     * @return 해당 날짜의 종료 (`23:59:59`)를 포함한 `LocalDateTime`
     */
    public static LocalDateTime atEndDateTime(LocalDate date) {
        if(date == null) {
            return null;
        }

        return date.atTime(23, 59, 59);
    }
}
