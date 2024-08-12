package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.client.ProductClient;
import com.sparta.msa_exam.order.core.client.ProductResponseDto;
import com.sparta.msa_exam.order.core.domain.Order;
import com.sparta.msa_exam.order.core.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, String userId) {
        // Check if products exist and if they have enough quantity
        // 오더의 제품을 한개씩 가져와 수량 체크하지 말고 오더의 모든 제품을 한번에 수량체크하는 법으로 구현해보기
        for (Long productId : requestDto.getOrderItemIds()) {
            ProductResponseDto product = productClient.getProduct(productId);
            log.info("############################ Product 수량 확인 : " + product.getQuantity());
            if (product.getQuantity() < 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with ID " + productId + " is out of stock.");
            }
        }

        // 주문 가능한 수량이라면 수량을 먼저 줄여줌 하지만 이렇게 하게 되면 수량을 줄이고 난 후 밑에서 order를 생성할때 오류가 발생하게 되면 주문은 되지 않고 수량만 주는 경우가 발생함
        // order 어플리케이션과 product어플리케이션은 별도로 구성되어 있으므로 msa에서 발생할 수 있는 문제점임
        // 따라서 이를 방지하기 위한 코드가 필요
        // Reduce the quantity of each product by 1
        for (Long productId : requestDto.getOrderItemIds()) {
            productClient.reduceProductQuantity(productId, 1);
        }


        Order order = Order.createOrder(requestDto.getOrderItemIds(), userId);
        Order savedOrder = orderRepository.save(order);
        return toResponseDto(savedOrder);
    }

    public Page<OrderResponseDto> getOrders(OrderSearchDto searchDto, Pageable pageable,String role, String userId) {
        return orderRepository.searchOrders(searchDto, pageable,role, userId);
    }
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));
        return toResponseDto(order);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto requestDto,String userId) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));

        order.updateOrder(requestDto.getOrderItemIds(), userId, OrderStatus.valueOf(requestDto.getStatus()));
        Order updatedOrder = orderRepository.save(order);

        return toResponseDto(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long orderId, String deletedBy) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));
        order.deleteOrder(deletedBy);
        orderRepository.save(order);
    }

    private OrderResponseDto toResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getStatus().name(),
                order.getCreatedAt(),
                order.getCreatedBy(),
                order.getUpdatedAt(),
                order.getUpdatedBy(),
                order.getOrderItemIds()
        );
    }
}