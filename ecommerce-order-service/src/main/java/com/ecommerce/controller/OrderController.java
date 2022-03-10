package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.jpa.OrderEntity;
import com.ecommerce.service.OrderService;
import com.ecommerce.vo.RequestOrder;
import com.ecommerce.vo.ResponseOrder;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order-service")
public class OrderController {
	@Autowired
	Environment env;
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
	}
	
	@PostMapping("/orders")
	public ResponseEntity<ResponseOrder> createOrder(@RequestBody RequestOrder requestOrder) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		OrderDto orderDto = mapper.map(requestOrder, OrderDto.class);
		orderService.createOrder(orderDto);
		ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity<ResponseOrder> getOrderByOrderId(@PathVariable("orderId")String orderId) {
		ModelMapper mapper = new ModelMapper();
		OrderDto orderDto = orderService.getOrderByOrderId(orderId);
		
		ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseOrder);
	}
	
	@GetMapping("/orders/{userId}")
	public ResponseEntity<List<ResponseOrder>> getOrderByUserId(
			@PathVariable("userId")String userId) {
		Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);
		List<ResponseOrder> result = new ArrayList<ResponseOrder>();
		
		orders.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseOrder.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
}
