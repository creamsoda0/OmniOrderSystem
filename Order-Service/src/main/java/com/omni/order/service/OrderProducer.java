package com.omni.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderCreate(Long orderId) {
        String message = "주문번호 " + orderId + "번이 접수되었습니다.";
        log.info("📤 [Kafka Producer] 메시지 발송: {}", message);

        // "order-create" 라는 토픽으로 메시지 전송
        kafkaTemplate.send("order-create", message);
    }
}