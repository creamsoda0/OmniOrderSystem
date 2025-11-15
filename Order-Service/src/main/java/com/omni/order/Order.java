package com.omni.order;

import jakarta.persistence.*;
import lombok.Data;

// com.omni.order.Order Service
@Data
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //주문 ID

    private Long productId; // 주문한 상품 ID (Product Service의 ID 참조)

    private Integer quantity; // 주문 수량

    private Integer totalPrice; //총 가격

    private String orderStatus; // 주문 상태 ( 예 : PENDING, COMPLETED, CANCELED)
}
