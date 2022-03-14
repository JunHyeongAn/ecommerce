package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDto;
import com.ecommerce.jpa.UserEntity;
import com.ecommerce.service.UserService;
import com.ecommerce.vo.Greeting;
import com.ecommerce.vo.RequestUser;
import com.ecommerce.vo.ResponseUser;

@RestController
public class UserController {
	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Greeting greeting;
	
	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in User Service"
				+ ", port(local.server.port)=" + env.getProperty("local.server.port")
				+ ", port(server.port)=" + env.getProperty("server.port")
				+ ", token secret=" + env.getProperty("token.secret")
				+ ", token expiration time=" + env.getProperty("token.expiration_time"));
	}
	
	@GetMapping("/welcome")
	public String welcome() {
//		return env.getProperty("greeting.message");
		return greeting.getMessage();
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<ResponseUser>> getUsers() {
		Iterable<UserEntity> userList = userService.getUserByAll();
		
		List<ResponseUser> result = new ArrayList<ResponseUser>();
		userList.forEach(v -> result.add(new ModelMapper().map(v, ResponseUser.class)));
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseUser> getUserByUserId(@PathVariable("userId")String userId) {
		UserDto userDto = userService.getUserByUserId(userId);
		ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);
		return ResponseEntity.status(HttpStatus.OK).body(responseUser);
	}
	
	@PostMapping("/users")
	public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = mapper.map(requestUser, UserDto.class);
		userService.createUser(userDto);
		
		ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}
}
