package com.omni.product;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
        //상품 ID로 상품을 찾는 기본 기능은 JpaRepository가 제공한다.
        // 필요하다면 여기에 사용자 정의 쿼리 메소드를 추가할 수 있다.
}

