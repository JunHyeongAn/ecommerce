package com.ecommerce.service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.jpa.UserEntity;

public interface UserService {
	UserDto createUser(UserDto userDto);
	
	UserDto getUserByUserId(String userId);
	Iterable<UserEntity> getUserByAll();
}
