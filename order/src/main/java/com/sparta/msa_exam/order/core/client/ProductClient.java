package com.sparta.msa_exam.order.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    // Product 어플리케이션을 통해 상품 모두 조회
    @GetMapping("/products")
    List<ProductResponseDto> getProduct();

}
