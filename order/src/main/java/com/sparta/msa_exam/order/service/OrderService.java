package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.core.client.ProductClient;
import com.sparta.msa_exam.order.core.domain.Order;
import com.sparta.msa_exam.order.core.domain.OrderMap;
import com.sparta.msa_exam.order.core.dto.CreateOrderRequest;
import com.sparta.msa_exam.order.core.dto.CreateOrderResponse;
import com.sparta.msa_exam.order.core.dto.OrderResponse;
import com.sparta.msa_exam.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    // 주문 추가
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest requestDto) {
        Order order = Order.create(requestDto.getName());
        Order savedOrder = orderRepository.save(order);
        return CreateOrderResponse.fromEntity(savedOrder);
    }

    // 주문 조회
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "order_cache")
    public OrderResponse getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponse::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // 주문 상품 추가
    public void addProduct(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId).orElseThrow();      // 기존에 존재하는 주문 entity를 가져옴

        productClient.getProduct().stream()
                .filter(i -> i.productId().equals(productId)).findAny()     // product 모듈의 모든 데이터 찾기에서 productId값과 같은 데이터만 추출
                .ifPresent(p -> order.addProduct(OrderMap.fromEntity(order,productId)));        // 해당 데이터를 Order Entity에 저장
    }




}