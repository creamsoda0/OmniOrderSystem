// Order-Service/src/main/java/com/omni/order/dto/AdminMessageCreateRequest.java
package com.omni.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Order-Service -> Message-Service 로 전송하는 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMessageCreateRequest {

    private String title;    // 예: "[ORDER CREATED] 주문번호 1"
    private String content;  // 메시지 본문
    private Long orderId;    // 관련 주문 ID
}

