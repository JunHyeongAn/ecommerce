package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.jpa.UserEntity;
import com.ecommerce.jpa.UserRepository;
import com.ecommerce.vo.ResponseOrder;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity entity = mapper.map(userDto, UserEntity.class);
		entity.setEnctyptedPwd(encoder.encode(userDto.getPwd()));
		
		userRepository.save(entity);
		
		UserDto returnUserDto = mapper.map(entity, UserDto.class);
		return returnUserDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null)
			throw new UsernameNotFoundException("User not found");
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		List<ResponseOrder> orders = new ArrayList<ResponseOrder>();
		userDto.setOrders(orders);
		
		return userDto;
	}

	@Override
	public Iterable<UserEntity> getUserByAll() {
		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		if(userEntity == null)
			throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmail(), userEntity.getEnctyptedPwd(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null)
			throw new UsernameNotFoundException(email);
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		return userDto;
	}
}
