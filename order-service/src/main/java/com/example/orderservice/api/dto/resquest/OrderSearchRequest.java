package com.example.orderservice.api.dto.resquest;

import com.example.orderservice.app.domain.enums.OrderStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class OrderSearchRequest {

    /** 주문 상태 */
    private OrderStatus status;

    /** 시작 일 */
    @PastOrPresent(message = "fromDate는 오늘 또는 과거 날짜여야 합니다.")
    private LocalDate fromDate;

    /** 종료 일 */
    private LocalDate toDate;

    @AssertTrue(message = "fromDate는 toDate보다 이전이어야 합니다.")
    public boolean isFromDateBeforeToDate() {
        if (fromDate == null || toDate == null) {
            return true;
        }
        return !fromDate.isAfter(toDate);
    }
}
