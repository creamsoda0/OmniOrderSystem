// Product-Service/src/main/java/com/omni/order/Main.java (패키지 이름이 com.omni.order로 되어 있는 것도 수정해야 합니다.)
package com.omni.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableFeignClients 제거 (이 서비스는 호출 주체가 아님)
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}