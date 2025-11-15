package com.omni.order.dto;

import lombok.Data;

@Data
public class OrderCreationRequest {
    private Long productId;
    private Integer quantity; // 주문 수량
}