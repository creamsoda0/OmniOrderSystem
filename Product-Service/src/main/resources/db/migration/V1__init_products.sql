DROP TABLE IF EXISTS products;

CREATE TABLE products (
    -- [1] 매핑: private Long productId
    product_id BIGSERIAL PRIMARY KEY,

    -- [2] 매핑: private String productName
    -- (스프링이 productName -> product_name으로 찾습니다)
    product_name VARCHAR(100) NOT NULL,

    -- [3] 매핑: private Integer stock
    stock INTEGER DEFAULT 0,

    -- [4] 매핑: private Integer price
    price INTEGER NOT NULL
);

-- 초기 데이터 넣기 (테스트용)
INSERT INTO products (product_name, stock, price) VALUES ('Test Product', 100, 10000);