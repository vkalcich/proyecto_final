package com.mitocode.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import com.mitocode.proyectofinal.controllers.AuthController;
import com.mitocode.proyectofinal.dtos.AuthenticationResponse;
import com.mitocode.proyectofinal.dtos.SignInDto;
import com.mitocode.proyectofinal.dtos.SignUpDto;

import io.jsonwebtoken.lang.Assert;

@SpringBootTest(classes = {AuthControllerTest.class})
public class AuthControllerTest {

	private AuthController authController;
	
	private SignInDto signin;
	
	private SignUpDto signup;
	
	@BeforeAll
	void setUp() {
		authController = Mockito.mock(AuthController.class);
		signin = new SignInDto();
		signup = new SignUpDto();
	}
	
	@BeforeTestMethod
	SignInDto generateSignin() {
		signin.setPassword("123");
		signin.setUsernameOrEmail("victor");
		return signin;
	}
	
	@Test
	void signinOk() {
		ResponseEntity<AuthenticationResponse> response = authController.findByUsername(signin);
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.ACCEPTED));
	}
	
	@BeforeTestMethod
	SignInDto generateSignup() {
		signup.setEmail("vkalcich@gmail.com");
		return signin;
	}
	
	@Test
	void emailExists() {
		ResponseEntity<?> response = authController.create(signup);
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

}
