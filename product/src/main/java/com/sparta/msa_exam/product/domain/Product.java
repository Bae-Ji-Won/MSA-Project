package com.sparta.msa_exam.product.domain;

import com.sparta.msa_exam.product.domain.dto.ProductRequest;
import com.sparta.msa_exam.product.domain.dto.ProductResponse;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer supplyPrice;



    public static Product createProduct(ProductRequest requestDto) {
        return Product.builder()
                .name(requestDto.name())
                .supplyPrice(requestDto.supplyPrice())
                .build();
    }


    // DTO로 변환하는 메서드
    public ProductResponse to() {
        return new ProductResponse(
                this.id,
                this.name,
                this.supplyPrice
        );
    }
}