package com.omni.order.feignclient;

import com.omni.order.dto.AdminMessageCreateRequest;
import com.omni.order.dto.AdminMessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Message-Service 의 /messages API 를 호출하는 Feign 클라이언트
 */
@FeignClient(name = "message-service", path = "/messages")
public interface MessageClient {

    @PostMapping
    AdminMessageResponse createAdminMessage(@RequestBody AdminMessageCreateRequest request);
}
