package com.omni.product.controller;


import com.omni.product.Product;
import com.omni.product.StockUpdateRequestDto;
import com.omni.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 상세 정보 조회 API (Order Service가 가격 및 재고 정보를 조회할 때 사용)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 재고 차감 API (Order Service가 주문 시 호출하는 핵심 API)
    @PostMapping("/deduct-stock")
    public ResponseEntity<String> deductStock(@RequestBody StockUpdateRequestDto request) {
        try {
            boolean success = productService.deductStock(request.getProductId(), request.getQuantity());

            if (success) {
                return ResponseEntity.ok("재고 차감 성공");
            } else {
                // 재고 부족 등의 경우 400 Bad Request 반환
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("재고가 부족합니다.");
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("상품을 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("재고 차감 중 오류 발생: " + e.getMessage());
        }
    }
}