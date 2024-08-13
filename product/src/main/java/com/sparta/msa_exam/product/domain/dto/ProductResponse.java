package com.sparta.msa_exam.product.domain.dto;


import com.sparta.msa_exam.product.domain.Product;

public record ProductResponse(
        Long productId,
        String name,
        Integer supplyPrice
) {

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getSupplyPrice());
    }
}