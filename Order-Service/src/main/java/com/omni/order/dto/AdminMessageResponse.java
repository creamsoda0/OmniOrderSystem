// Order-Service/src/main/java/com/omni/order/dto/AdminMessageResponse.java
package com.omni.order.dto;

import lombok.Data;

/**
 * Message-Service -> Order-Service 로 돌아오는 응답 DTO
 */
@Data
public class AdminMessageResponse {

    private Long id;       // 메시지 PK
    private String title;
    private String content;
}
