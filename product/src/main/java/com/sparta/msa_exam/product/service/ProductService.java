package com.sparta.msa_exam.product.service;



import com.sparta.msa_exam.product.domain.Product;
import com.sparta.msa_exam.product.domain.dto.ProductRequest;
import com.sparta.msa_exam.product.domain.dto.ProductResponse;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest requestDto, String userId) {
        Product product = Product.createProduct(requestDto);
        Product savedProduct = productRepository.save(product);
        return savedProduct.to();
    }

    // 상품 목록 조회 Page
    public Page<ProductResponse> getProducts(Pageable pageable) {
        return productRepository.searchProducts(pageable);
    }

    // 상품 목록 조회 List
    public List<ProductResponse> getProductsList() {
        return productRepository.findAll().stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }



}