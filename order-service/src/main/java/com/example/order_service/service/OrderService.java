package com.example.order_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.order_service.client.InventoryClient;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    
    public void placeOrder(OrderRequest orderRequest) {

        // Check inventory

        if (!inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity())) {
            throw new RuntimeException("Out of stock");
        }
        
        // Place order logic
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());

        orderRepository.save(order);

    }
}
