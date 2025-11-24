package com.omni.order.controller;


import com.omni.order.Order;
import com.omni.order.dto.OrderCreationRequest;
import com.omni.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/orders") // 기본 경로: /orders
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService; // 비즈니스 로직 담당 서비스 주입

    @GetMapping
    public ModelAndView goIndex() {
        return new ModelAndView("forward:/index.html");
    }    
    /**
     * 주문 생성 API (POST /orders)
     * 주문 요청을 받아 OrderService를 통해 재고 차감 및 주문 저장을 시도합니다.
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderCreationRequest request) {
        try {
            // 1. 주문 서비스 호출 (여기서 Feign Client를 통해 Product Service 호출 발생)
            Order newOrder = orderService.createOrder(request);

            // 2. 성공 시: HTTP 201 Created 응답 및 생성된 주문 정보 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);

        } catch (RuntimeException e) {
            // 3. 실패 시: RuntimeException (주로 재고 확보 불가) 발생 시
            // HTTP 400 Bad Request 반환 (재고 부족 등의 클라이언트 오류)
            System.err.println("주문 생성 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}