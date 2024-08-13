package com.sparta.msa_exam.order.core.dto;

import com.sparta.msa_exam.order.core.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {
    private Long orderId;
    private String name;


    public static CreateOrderResponse fromEntity(Order order) {
        return new CreateOrderResponse(
                order.getId(),
                order.getName()
        );
    }
}
