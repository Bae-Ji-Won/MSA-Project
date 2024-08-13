package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.core.dto.AddProductRequest;
import com.sparta.msa_exam.order.core.dto.CreateOrderRequest;
import com.sparta.msa_exam.order.core.dto.CreateOrderResponse;
import com.sparta.msa_exam.order.core.dto.OrderResponse;
import com.sparta.msa_exam.order.service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    @Value("${server.port}")
    private String serverPort;

    // Header에 서버포트 추가하는 메서드
    @InitBinder
    public void initBinder(HttpServletResponse response) {
        response.setHeader("Server-Port", serverPort);
        System.out.println(response.getHeader("Server-Port"));
    }

    // 주문 추가
    @PostMapping
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest);
    }
    
    // 주문 단건 조회 확인
    @GetMapping("/{orderId}")
    public OrderResponse getOrders(@PathVariable(name = "orderId")Long orderId) {
        return orderService.getOrder(orderId);
    }
    
    // 주문 상품 추가(FeignClient 사용)
    @PutMapping("/{orderId}")
    public void addProduct(@PathVariable(name = "orderId")Long orderId, @RequestBody AddProductRequest request){
        orderService.addProduct(orderId,request.postId());
    }
}
