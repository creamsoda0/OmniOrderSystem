// Order-Service/src/main/java/com/omni/order/Main.java (Order Service의 메인 클래스)
package com.omni.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient // 유레카 클라이언트 활성화
@EnableFeignClients
//@ComponentScan(basePackages = "com.omni.order")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}