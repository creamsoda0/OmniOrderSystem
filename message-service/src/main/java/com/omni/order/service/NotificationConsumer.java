package com.omni.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {

    // "order-create" 토픽을 구독(Listen)
    @KafkaListener(topics = "order-create", groupId = "notification-group")
    public void receiveMessage(String message) {
        log.info("📩 [Kafka Consumer] 관리자 알림 수신: {}", message);

        // 여기에 나중에 이메일 보내기, 웹소켓 알림 로직 등을 넣으면 됩니다.
    }
}
