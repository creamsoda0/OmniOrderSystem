package com.omni.eureka;// eureka-server/src/main/java/com/omni/eureka/Main.java (예시)

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer; // 추가

@SpringBootApplication
@EnableEurekaServer // 유레카 서버 활성화
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}