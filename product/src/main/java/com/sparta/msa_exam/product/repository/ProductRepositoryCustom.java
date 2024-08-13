package com.sparta.msa_exam.product.repository;

import com.sparta.msa_exam.product.domain.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductResponse> searchProducts(Pageable pageable);
}
