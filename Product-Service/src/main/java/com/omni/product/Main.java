// Product-Service/src/main/java/com/omni/order/Main.java (패키지 이름이 com.omni.order로 되어 있는 것도 수정해야 합니다.)
package com.omni.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 유레카 클라이언트 활성화
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}