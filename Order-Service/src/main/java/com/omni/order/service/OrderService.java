package com.omni.order.service;


import com.omni.order.Order;
import com.omni.order.ProductClient;
import com.omni.order.dto.OrderCreationRequest;
import com.omni.order.dto.StockUpdateRequestDto;
import com.omni.order.repository.OrderRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private ProductClient productClient; // Feign Client 주입

    @Autowired
    private OrderRepository orderRepository; // 방금 작성한 Repository 주입

    @Transactional
    public Order createOrder(OrderCreationRequest request) {

        StockUpdateRequestDto stockRequest = new StockUpdateRequestDto();
        stockRequest.setProductId(request.getProductId());
        stockRequest.setQuantity(request.getQuantity());

        try {
            // 1. Feign Client 호출 (성공 시 200 OK)
            String productResponse = productClient.deductStock(stockRequest);

            // Product Service가 항상 200 OK를 반환하고 응답 메시지로 성공/실패를 알려주는 경우에만 이 로직을 사용
            if (!productResponse.contains("재고 차감 성공")) {
                // 400이 아닌 200을 받았지만, 로직상 실패 메시지인 경우
                throw new RuntimeException("상품 서비스 로직 실패: " + productResponse);
            }

        } catch (FeignException e) {
            // 2. FeignException 발생 시 (4xx 또는 5xx 오류)
            System.err.println("Feign 통신 오류: " + e.getMessage());

            if (e.status() == 400) {
                // 재고 부족 (400 Bad Request)인 경우
                // Feign Client 응답 본문에서 상세 오류 메시지를 추출할 수 있음 (e.contentUTF8())
                throw new RuntimeException("주문 실패: 재고 부족", e);
            } else if (e.status() == 404) {
                // 상품 없음 (404 Not Found)인 경우
                throw new RuntimeException("주문 실패: 상품 찾을 수 없음", e);
            } else {
                // 기타 서버 오류 (5xx) 또는 예상치 못한 오류
                throw new RuntimeException("주문 실패: 상품 서비스 오류", e);
            }

        } catch (Exception e) {
            // 네트워크, I/O 등 Feign 통신 외의 예외 처리
            System.err.println("네트워크 또는 기타 예외: " + e.getMessage());
            throw new RuntimeException("주문 실패: 통신 불가", e);
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