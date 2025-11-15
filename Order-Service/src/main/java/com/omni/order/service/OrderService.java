package com.omni.order.service;


import com.omni.order.Order;
import com.omni.order.ProductClient;
import com.omni.order.dto.OrderCreationRequest;
import com.omni.order.dto.StockUpdateRequestDto;
import com.omni.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private ProductClient productClient; // Feign Client 주입

    @Autowired
    private OrderRepository orderRepository; // 방금 작성한 Repository 주입

    @Transactional // 주문 생성은 트랜잭션으로 묶여야 합니다.
    public Order createOrder(OrderCreationRequest request) {

        // 1. 재고 차감 요청 DTO 생성
        StockUpdateRequestDto stockRequest = new StockUpdateRequestDto();
        stockRequest.setProductId(request.getProductId());
        stockRequest.setQuantity(request.getQuantity());

        // 2. Product Service의 재고 차감 API 호출 (MSA 통신)
        try {
            // Feign Client 호출. 재고 부족 시 Product Service에서 400 에러를 반환
            // Feign은 이 에러를 FeignException으로 변환합니다.
            productClient.deductStock(stockRequest);
        } catch (Exception e) {
            // 재고 차감 실패 시 (Product Service에서 에러 반환), 주문 생성 트랜잭션 롤백
            System.err.println("재고 차감 실패: " + e.getMessage());
            throw new RuntimeException("주문 실패: 재고 확보 불가");
        }

        // 3. 재고 차감 성공 시, 주문 정보 저장
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setOrderStatus("COMPLETED");

        // 총 가격은 Product Service의 API를 한 번 더 호출해서 받아와야 하지만,
        // 현재는 간단히 0으로 설정하거나, OrderCreationRequest에 포함시키는 것으로 가정합니다.
        order.setTotalPrice(request.getQuantity() * 10000); // 임시 가격 설정

        return orderRepository.save(order);
    }
}