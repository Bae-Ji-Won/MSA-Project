package com.sparta.msa_exam.product.domain.dto;


public record ProductRequest(
        String name,
        Integer supplyPrice
){
}