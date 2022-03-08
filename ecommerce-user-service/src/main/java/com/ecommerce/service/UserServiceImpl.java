package com.ecommerce.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.jpa.UserEntity;
import com.ecommerce.jpa.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity entity = mapper.map(userDto, UserEntity.class);
		entity.setEnctyptedPwd("encryptedPwd");
		
		userRepository.save(entity);
		return null;
	}
}
