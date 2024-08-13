package com.sparta.msa_exam.order.core.client;



public record ProductResponseDto(
        Long productId,
        String name,
        Integer supplyPrice
) {
}