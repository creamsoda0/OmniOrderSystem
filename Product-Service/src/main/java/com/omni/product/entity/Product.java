package com.omni.product.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products") // 소문자 권장
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long productId;

    private String productName; // 상품명

    private Integer stock; // 재고 수량 (소문자로 수정함)

    private Integer price; // 가격
}