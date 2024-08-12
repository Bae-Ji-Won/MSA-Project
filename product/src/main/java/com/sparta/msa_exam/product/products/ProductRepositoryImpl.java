package com.sparta.msa_exam.product.products;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.msa_exam.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.msa_exam.product.core.QProduct.product;


@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductResponseDto> searchProducts(ProductSearchDto searchDto, Pageable pageable) {
        // 정렬 기준 설정
//        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        QueryResults<Product> results = queryFactory
                .selectFrom(product)
                .where(
                        nameContains(searchDto.getName()),
                        descriptionContains(searchDto.getDescription()),
                        priceBetween(searchDto.getMinPrice(), searchDto.getMaxPrice()),
                        quantityBetween(searchDto.getMinQuantity(), searchDto.getMaxQuantity())
                )
                // 정렬
//                .orderBy(orders.toArray(new OrderSpecifier[0]))
                // 페이징 처리
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                // 결과값 반환
                .fetchResults();

        List<ProductResponseDto> content = results.getResults().stream()
                .map(Product::toResponseDto)
                .collect(Collectors.toList());
        long total = results.getTotal();

        // PageImpl 객체를 생성하여 결과를 반환합니다. 이 객체는 페이지 내용, 페이지 정보, 총 결과 수를 포함
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression nameContains(String name) {
        // containsIgnoreCase : 문자열 검색을 수행하는 메서드, 대소문자를 구분하지 않음
        return name != null ? product.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression descriptionContains(String description) {
        return description != null ? product.description.containsIgnoreCase(description) : null;
    }

    private BooleanExpression priceBetween(Double minPrice, Double maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return product.price.between(minPrice, maxPrice);
        } else if (minPrice != null) {
            return product.price.goe(minPrice);
        } else if (maxPrice != null) {
            return product.price.loe(maxPrice);
        } else {
            return null;
        }
    }

    private BooleanExpression quantityBetween(Integer minQuantity, Integer maxQuantity) {
        if (minQuantity != null && maxQuantity != null) {
            return product.quantity.between(minQuantity, maxQuantity);
        } else if (minQuantity != null) {
            return product.quantity.goe(minQuantity);
        } else if (maxQuantity != null) {
            return product.quantity.loe(maxQuantity);
        } else {
            return null;
        }
    }

    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        // pageable 객체에서 정렬 정보가 존재하는지 확인합니다. 만약 정렬정보가 존재한다면
        if (pageable.getSort() != null) {
            // pageable.getSort()로부터 반환된 Sort.Order 객체들을 반복
            for (Sort.Order sortOrder : pageable.getSort()) {

                com.querydsl.core.types.Order direction = sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
                switch (sortOrder.getProperty()) {
                    case "createdAt":
                        orders.add(new OrderSpecifier<>(direction, product.createdAt));
                        break;
                    case "price":
                        orders.add(new OrderSpecifier<>(direction, product.price));
                        break;
                    case "quantity":
                        orders.add(new OrderSpecifier<>(direction, product.quantity));
                        break;
                    default:
                        break;
                }
            }
        }

        return orders;
    }
}