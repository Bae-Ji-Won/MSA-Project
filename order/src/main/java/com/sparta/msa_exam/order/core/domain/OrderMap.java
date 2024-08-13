package com.sparta.msa_exam.order.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.PageRequest;

@Entity
@Table(name = "order_map")
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OrderMap {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

    public static OrderMap fromEntity(Order order, Long productId) {
        return OrderMap.builder()
                .order(order)
                .productId(productId)
                .build();
    }


}
