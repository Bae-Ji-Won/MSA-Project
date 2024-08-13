package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.domain.dto.ProductRequest;
import com.sparta.msa_exam.product.domain.dto.ProductResponse;
import com.sparta.msa_exam.product.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Value("${server.port}")
    private String serverPort;

    // ServerPort 추가
    @InitBinder
    public void initBinder(HttpServletResponse response) {
        response.setHeader("Server-Port", serverPort);
        System.out.println(response.getHeader("Server-Port"));
    }

    // 상품 생성
    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest,
                                         @RequestHeader(value = "X-User-Id", required = true) String userId,
                                         @RequestHeader(value = "X-Role", required = true) String role) {
        if (!"MANAGER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER.");
        }

        return productService.createProduct(productRequest, userId);
    }

//    // 상품 모두 출력 - 페이징 처리
//    @GetMapping
//    public Page<ProductResponse> getProducts(Pageable pageable) {
//        return productService.getProducts(pageable);
//    }

    // 상품 모두 출력 - List 처리 (Order 전용)
    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.getProductsList();
    }
}
