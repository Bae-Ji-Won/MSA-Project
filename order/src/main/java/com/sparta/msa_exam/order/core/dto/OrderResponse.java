package com.sparta.msa_exam.order.core.dto;

import com.sparta.msa_exam.order.core.domain.Order;
import com.sparta.msa_exam.order.core.domain.OrderMap;

import java.util.List;
import java.util.stream.Collectors;

public record OrderResponse(
        Long orderId,
        List<Long> productIds
) {

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getProductIds().stream().map(OrderMap::getProductId).collect(Collectors.toList())
        );
    }
}
