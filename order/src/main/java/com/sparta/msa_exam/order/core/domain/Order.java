package com.sparta.msa_exam.order.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderMap> productIds;


    public static Order create(String name) {
        return Order.builder()
                .name(name)
                .build();
    }

    public void addProduct(OrderMap product) {
        productIds.add(product);
    }
}

