// Product Service
package com.omni.product;

import jakarta.persistence.*;
import lombok.Data; //Lombok 사용

@Data
@Entity
@Table (name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //상품 ID

    private String name; //상품명

    private Integer Stock; //재고 수량 <--- 핵심 관리 대상

    private Integer price; //가격
}