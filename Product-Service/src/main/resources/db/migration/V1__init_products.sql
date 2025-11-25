CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,       -- 자동 증가 ID
    name VARCHAR(100) NOT NULL,          -- 상품명
    description TEXT,                    -- 상품 설명
    price NUMERIC(10, 2) NOT NULL,       -- 가격 (소수점 2자리까지)
    stock_quantity INTEGER DEFAULT 0,    -- 재고 수량
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 등록일
);