package com.ecommerce.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDto;
import com.ecommerce.service.UserService;
import com.ecommerce.vo.Greeting;
import com.ecommerce.vo.RequestUser;

@RestController
@RequestMapping("/user-service")
public class UserController {
//	@Autowired
//	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Greeting greeting;
	
	@GetMapping("health_check")
	public String status() {
		return "It's Working in User Service";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
//		return env.getProperty("greeting.message");
		return greeting.getMessage();
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody RequestUser requestUser) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(requestUser, UserDto.class);
		userService.createUser(userDto);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
}
