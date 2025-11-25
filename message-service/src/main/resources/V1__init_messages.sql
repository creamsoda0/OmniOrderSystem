CREATE TABLE messages (
    message_id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES orders(order_id) ON DELETE SET NULL, -- 특정 주문 관련 문의인 경우 연결 (없으면 NULL)
    sender_name VARCHAR(100) NOT NULL,   -- 보낸 사람
    content TEXT NOT NULL,               -- 메시지 내용
    is_read BOOLEAN DEFAULT FALSE,       -- 읽음 여부 확인
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);