package com.ecommerce.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RequestUser {
	
	@NotNull(message = "Email connot be null")
	@Size(min = 2, message = "Email not be less than two chracters")
	@Email
	private String email;
	
	@NotNull(message = "Name cannot be null")
	@Size(min = 2, message = "Name not be less than two chracters")
	private String name;
	
	@NotNull(message = "Password cannot be null")
	@Size(min = 8, message = "Passwrod be equal or grater than 8 characters")
	private String pwd;
}
