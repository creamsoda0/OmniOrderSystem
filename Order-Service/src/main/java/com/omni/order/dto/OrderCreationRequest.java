package com.omni.order.dto;

import lombok.Data;

@Data
public class OrderCreationRequest {
    private Long productId;
    private Integer quantity; // 주문 수량

    // [추가] 주문자 ID는 필수입니다!
    private Long userId;

    // (선택) 가격을 클라이언트에서 받는다면 추가.
    // 보통은 서버가 ProductService에 물어봐서 계산합니다.
    private Integer totalPrice;

    // (선택) 상태도 서버가 초기값(PENDING)으로 설정하는게 맞지만,
    // DTO에 넣고 싶다면 추가하세요.
    private String orderStatus;
}