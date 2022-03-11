package com.ecommerce.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ecommerce.dto.UserDto;
import com.ecommerce.jpa.UserEntity;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto userDto);
	
	UserDto getUserByUserId(String userId);
	Iterable<UserEntity> getUserByAll();
}
