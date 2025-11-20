package com.omni.product.service;


import com.omni.product.Product;
import com.omni.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // (추후 Redis 캐싱을 적용할 위치)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다: " + id));
    }

    /**
     * 재고를 차감하는 메서드 (트랜잭션 관리 필수)
     * @param productId 상품 ID
     * @param quantity 차감할 수량
     * @return 성공 여부
     */
    @Transactional
    public boolean deductStock(Long productId, Integer quantity) {
        // 1. 상품을 DB에서 조회 (락을 걸지 않아 동시성 문제가 발생할 수 있지만, 토이 프로젝트에서는 일단 진행)
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다: " + productId));

        // 2. 재고 확인
        if (product.getStock() < quantity) {
            // 재고 부족
            return false;
        }

        // 3. 재고 차감 및 저장
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        // 커밋 시점에 DB에 반영됨
        return true;
    }

    @Transactional
    public void restoreStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다: " + productId));

        // 재고 증가 (차감의 반대)
        int currentStock = product.getStock();
        product.setStock(currentStock + quantity);

        productRepository.save(product);

        // 로그를 통해 복구 성공 확인
        log.info("✅ 재고 복구 완료: 상품 ID={}, 복구 수량={}, 현재 재고={}",
                productId, quantity, product.getStock());
    }
}