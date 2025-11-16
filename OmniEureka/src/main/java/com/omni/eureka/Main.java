package com.omni.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer; // 👈 import 필요

@SpringBootApplication
@EnableEurekaServer // 👈 Eureka 서버 역할을 활성화합니다.
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}