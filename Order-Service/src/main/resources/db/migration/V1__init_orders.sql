-- 기존 테이블 삭제 (깔끔하게 다시 시작)
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
    -- [1] 매핑: private Long orderId
    order_id BIGSERIAL PRIMARY KEY,

    -- [2] 매핑: private Long productId
    product_id BIGINT NOT NULL,

    -- [3] 매핑: private Integer quantity
    quantity INTEGER NOT NULL,

    -- [4] 매핑: private Integer totalPrice
    -- (자바가 totalPrice이므로, SQL은 total_price여야 함)
    total_price INTEGER NOT NULL,

    -- [5] 매핑: private String orderStatus
    -- (자바가 orderStatus이므로, SQL은 order_status여야 함)
    order_status VARCHAR(50),

    -- [6] 매핑: private LocalDateTime createdAt
    -- (자바가 createdAt이므로, SQL은 created_at이어야 함)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 초기 데이터 (테스트용)
-- 상품ID 1번을 2개 주문했고, 총 가격은 20000원, 상태는 PENDING
INSERT INTO orders (product_id, quantity, total_price, order_status)
VALUES (1, 2, 20000, 'PENDING');