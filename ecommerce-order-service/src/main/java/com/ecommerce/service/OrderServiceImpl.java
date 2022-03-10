package com.ecommerce.service;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.jpa.OrderEntity;
import com.ecommerce.jpa.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public OrderDto createOrder(OrderDto orderDetails) {
		ModelMapper mapper = new ModelMapper();
		orderDetails.setTotalPrice(
			orderDetails.getQty() * orderDetails.getUnitPrice()
		);
		orderDetails.setOrderId(UUID.randomUUID().toString());
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		OrderEntity entity = mapper.map(orderDetails, OrderEntity.class);
		entity.setCreatedAt(new Date());
		orderRepository.save(entity);
		return orderDetails;
	}
	@Override
	public OrderDto getOrderByOrderId(String orderId) {
		OrderEntity entity = orderRepository.findByOrderId(orderId);
		return new ModelMapper().map(entity, OrderDto.class);
	}
	@Override
	public Iterable<OrderEntity> getOrdersByUserId(String userId) {
		return orderRepository.findByUserId(userId);
	}
}
