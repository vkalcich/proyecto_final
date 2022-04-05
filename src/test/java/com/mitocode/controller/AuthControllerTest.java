package com.mitocode.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.mitocode.proyectofinal.controllers.AuthController;
import com.mitocode.proyectofinal.dtos.AuthenticationResponse;
import com.mitocode.proyectofinal.dtos.SignInDto;
import com.mitocode.proyectofinal.dtos.SignUpDto;
import com.mitocode.proyectofinal.exceptions.ResourceNotFoundException;
import com.mitocode.proyectofinal.security.JWTUtils;
import com.mitocode.proyectofinal.services.UserService;
import com.mitocode.proyectofinal.services.impl.UserServiceImpl;

import io.jsonwebtoken.lang.Assert;

//@SpringBootTest(classes = {AuthControllerTest.class})
public class AuthControllerTest {

	private AuthController authController;
	private SignInDto signin = new SignInDto();
	private SignUpDto signup;
	private AuthenticationManager authenticationManager;
	private UserService userService;
	private JWTUtils jwtUtils;
	
	@BeforeEach
	void setUp() {
		authenticationManager = Mockito.mock(AuthenticationManager.class);
		userService = Mockito.mock(UserServiceImpl.class);
		jwtUtils = Mockito.mock(JWTUtils.class);
		authController = new AuthController(authenticationManager, userService, jwtUtils);
		signin.setPassword("123");
		signin.setUsernameOrEmail("victor");
		signup = new SignUpDto();
	}
	
	@Test
	void signinOk() {
		Authentication authentication = new UsernamePasswordAuthenticationToken(signin.getUsernameOrEmail(), signin.getPassword());
		
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				signin.getUsernameOrEmail(), signin.getPassword()))).thenReturn(authentication);
		
		ResponseEntity<AuthenticationResponse> response = authController.findByUsername(signin);
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.ACCEPTED));
	}

	
	@Test
	void signinNotOk() {
		signin = new SignInDto();
		signin.setPassword("1234");
		signin.setUsernameOrEmail("victor");
		Exception thrown = Assertions.assertThrows(Exception.class, () -> 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signin.getUsernameOrEmail(), signin.getPassword())));
		
		ResponseEntity<AuthenticationResponse> response = authController.findByUsername(signin);
		
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.FORBIDDEN));
	}

}
