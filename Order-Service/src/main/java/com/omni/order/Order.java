package com.omni.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders") // PostgreSQL은 소문자 테이블명이 안전합니다.
public class Order {

    @Id
    // Oracle의 SEQUENCE 대신 PostgreSQL의 IDENTITY(자동 증가) 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId; // 주문한 상품 ID

    private Integer quantity; // 주문 수량

    private Integer totalPrice; // 총 가격

    private String orderStatus; // 주문 상태

    @CreationTimestamp // 주문 생성 시 시간 자동 저장
    @Column(updatable = false)
    private LocalDateTime createdAt;
}