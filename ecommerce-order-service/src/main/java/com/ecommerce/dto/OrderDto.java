package com.ecommerce.dto;

import lombok.Data;

@Data
public class OrderDto {
	private String productId;
	private Integer qty;
	private Integer unitPrice;
	private String userId;
	private Integer totalPrice;	
	private String orderId;
}
