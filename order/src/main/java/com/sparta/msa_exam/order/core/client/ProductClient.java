package com.sparta.msa_exam.order.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {

    // Product 어플리케이션을 통해 상품 조회
    @GetMapping("/products/{id}")
    ProductResponseDto getProduct(@PathVariable("id") Long id);

    // 수량 줄이기
    @GetMapping("/products/{id}/reduceQuantity")
    void reduceProductQuantity(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);
}
