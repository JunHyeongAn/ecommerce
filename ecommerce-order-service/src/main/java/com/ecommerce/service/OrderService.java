package com.ecommerce.service;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.jpa.OrderEntity;

public interface OrderService {
	Iterable<OrderEntity> getOrdersByUserId(String userId);
	OrderDto createOrder(OrderDto orderDetails);
	OrderDto getOrderByOrderId(String orderId);
}
