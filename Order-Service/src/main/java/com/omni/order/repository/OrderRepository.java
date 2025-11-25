package com.omni.order.repository;


import com.omni.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 1. @Repository: 스프링에게 이 인터페이스가 데이터 접근 계층임을 알립니다. (선택적이지만 명시적이라 좋음)
@Repository
// 2. JpaRepository 상속: Order 엔티티와 PK 타입(Long)을 지정합니다.
//    이것만으로 findById, findAll, save, delete 등의 메서드가 자동 생성됩니다.
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 추가적인 쿼리 메소드(예: 주문 상태로 찾기 등)가 필요할 때 여기에 선언합니다.
    // 현재는 아무 내용도 필요 없습니다.
}