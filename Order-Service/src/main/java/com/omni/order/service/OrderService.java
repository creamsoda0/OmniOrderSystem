package com.omni.order.service;


import com.omni.order.entity.Order;
import com.omni.order.feignclient.ProductClient;
import com.omni.order.dto.OrderCreationRequest;
import com.omni.order.dto.StockUpdateRequestDto;
import com.omni.order.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {


    private final ProductClient productClient; // Feign Client 주입

    private final OrderRepository orderRepository; // 방금 작성한 Repository 주입

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

                //  임시 테스트 코드 시작
                // ProductService의 버그로 인해 400 에러가 났어도 재고가 줄었으므로,
                // 이 시점에서 재고 복구(restoreStock)를 강제로 시도합니다.
                /*log.warn(" 400 에러 발생. Product Service 버그로 재고가 줄었을 가능성 있음. 강제로 복구 호출 시작.");

                try {
                    productClient.restoreStock(stockRequest);
                    log.info("✅ 강제 재고 복구 성공: 상품 ID={}, 수량={}", stockRequest.getProductId(), stockRequest.getQuantity());
                } catch (Exception restoreException) {
                    log.error(" 치명적 오류: 400 에러 상황에서 재고 복구마저 실패.", restoreException);
                }*/
                //  임시 테스트 코드 끝

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
        order.setTotalPrice(request.getQuantity() * 10000);
        // 임시 가격으로 지정해놓았으나 상품 종류 추가시 수정 필요

        // 총 가격은 Product Service의 API를 한 번 더 호출해서 받아와야 하지만,
        // 현재는 간단히 0으로 설정하거나, OrderCreationRequest에 포함시키는 것으로 가정합니다.
        order.setTotalPrice(request.getQuantity() * 10000); // 임시 가격 설정

       try {
            // 주문 정보 저장 시도 (Order DB 접근)
           Order savedOrder = orderRepository.save(order);

           // 테스트코드 (테스트가 종료되면 주석처리해주세요)
 /*          if (order.getQuantity() == 1) { // 예시: 수량이 1개인 경우에만 오류 유발
               throw new RuntimeException("재고 차감 후, 후속 로직 강제 오류 발생!");
           }*/
            // 테스트 코드 끝
           return savedOrder;

        } catch (Exception dbException) {
            // 3. 주문 저장 실패 시 (Order DB 오류) - 보상 트랜잭션 시작!
            log.error("Error occurred while saving Order DB data. Initiating stock restoration. Error: {}", dbException.getMessage());

            // 4. Product Service에 재고 복구 요청 (Compensation)
            try {
                productClient.restoreStock(stockRequest);
                log.info(" 재고 복구 성공: 상품 ID={}, 수량={}", stockRequest.getProductId(), stockRequest.getQuantity());
            } catch (Exception restoreException) {
                //  재고 복구까지 실패하면 수동 개입이 필요
                log.error(" 치명적 오류: 재고 복구까지 실패했습니다. 상품 ID={}, 오류: {}",
                        stockRequest.getProductId(), restoreException.getMessage());
            }

            // 5. 주문 실패를 알리기 위해 예외를 던져 @Transactional 롤백
            throw new RuntimeException("Failed to process order: Database storage error", dbException);
        }



    }


}
