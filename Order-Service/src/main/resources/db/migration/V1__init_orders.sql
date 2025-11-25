-- 주문 테이블 생성 (테이블 명을 orders로 지정)

CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,         -- 자동 증가 ID
    customer_name VARCHAR(100) NOT NULL, -- 주문자명 (실제로는 user_id 등을 사용)
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 주문 일시
    status VARCHAR(20) DEFAULT 'PENDING', -- 주문 상태 (PENDING, COMPLETED, CANCELLED 등)
    total_amount NUMERIC(10, 2) DEFAULT 0 -- 총 주문 금액
);

CREATE TABLE order_items (
    item_id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES orders(order_id) ON DELETE CASCADE,
    product_id INTEGER REFERENCES products(product_id),
    quantity INTEGER NOT NULL CHECK (quantity > 0), -- 주문 수량
    unit_price NUMERIC(10, 2) NOT NULL              -- 주문 시점의 가격 (가격 변동 대비)
);
-- (참고) 만약 외래키(FK)를 걸고 싶다면 나중에 V2__add_fk.sql 같은 파일을 만들어서 추가하면 됩니다.