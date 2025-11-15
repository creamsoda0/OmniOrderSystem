package com.omni.product;

import lombok.Data;

@Data
public class StockUpdateRequestDto {
    private Long productId;
    private Integer quantity; //차감할 수량
}
