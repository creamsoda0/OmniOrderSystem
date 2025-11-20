package com.omni.order;

import com.omni.order.dto.StockUpdateRequestDto; // DTO는 Order Service에 복사/정의해야 합니다.

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", path = "/products")
public interface ProductClient {
    /**
     * Product Service의 재고 차감 API (POST /products/deduct-stock)를 호출합니다.
     */
    @PostMapping("/deduct-stock")
    String deductStock(@RequestBody StockUpdateRequestDto request);

    @PostMapping("/restore-stock")
    ResponseEntity<Void> restoreStock(@RequestBody StockUpdateRequestDto request);
}