package com.sparta.msa_exam.product.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.msa_exam.product.domain.Product;
import com.sparta.msa_exam.product.domain.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.msa_exam.product.domain.QProduct.product;


@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductResponse> searchProducts(Pageable pageable) {

        QueryResults<Product> results = queryFactory
                .selectFrom(product)

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                // 결과값 반환
                .fetchResults();

        List<ProductResponse> content = results.getResults().stream()
                .map(Product::to)
                .collect(Collectors.toList());
        long total = results.getTotal();

        // PageImpl 객체를 생성하여 결과를 반환합니다. 이 객체는 페이지 내용, 페이지 정보, 총 결과 수를 포함
        return new PageImpl<>(content, pageable, total);
    }
}