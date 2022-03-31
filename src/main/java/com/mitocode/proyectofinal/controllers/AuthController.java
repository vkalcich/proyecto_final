package com.mitocode.proyectofinal.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.proyectofinal.dtos.AuthenticationResponse;
import com.mitocode.proyectofinal.dtos.SignInDto;
import com.mitocode.proyectofinal.dtos.SignUpDto;
import com.mitocode.proyectofinal.dtos.UserDto;
import com.mitocode.proyectofinal.security.JWTUtils;
import com.mitocode.proyectofinal.services.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
    private AuthenticationManager authenticationManager;

	@Qualifier(value = "UserService")
	private UserService userService;
	
	private JWTUtils jwtUtils;
	
	public AuthController(AuthenticationManager authenticationManager, UserService userService, JWTUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtUtils = jwtUtils;
	}
	
	@ApiOperation(value = "Inicio de sesion")
	@PostMapping("/signin")
	public ResponseEntity<AuthenticationResponse> findByUsername(@RequestBody SignInDto signInDto){
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				signInDto.getUsernameOrEmail(), signInDto.getPassword()));
			
			String token = jwtUtils.generateToken(authentication);
			
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(token), HttpStatus.ACCEPTED);
		
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
		}
		
		
	}
	
	@ApiOperation(value = "Creacion un usuario")
	@PostMapping("/signup")
	public ResponseEntity<UserDto> create(@RequestBody SignUpDto signUpDto){
		
		UserDto userDto = this.userService.findUserByUsernameOrEmail(signUpDto.getUsername(), signUpDto.getEmail());
		
		
		UserDto newUserDto = this.userService.save(userDto);
		return new ResponseEntity<UserDto>(newUserDto, HttpStatus.CREATED);
	}
	
}
