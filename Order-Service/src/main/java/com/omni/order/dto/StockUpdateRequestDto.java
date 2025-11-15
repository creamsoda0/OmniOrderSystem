package com.omni.order.dto;

import lombok.Data;

@Data
public class StockUpdateRequestDto {
    private Long productId;
    private Integer quantity;
}