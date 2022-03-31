package com.mitocode.proyectofinal.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mitocode.proyectofinal.dtos.UserDto;
import com.mitocode.proyectofinal.entities.User;
import com.mitocode.proyectofinal.exceptions.ResourceNotFoundException;
import com.mitocode.proyectofinal.repositories.UserRepository;
import com.mitocode.proyectofinal.services.UserService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service("UserService")
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private ModelMapper modelMapper;
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserDto findUserByUsernameOrEmail(String username, String email) {
		User user = this.userRepository.findByUsernameOrEmail(username, email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username or email", username + "-" + email));
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto save(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User newUser = this.userRepository.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

	@Override
	public Boolean existsUserByUsername(String username) {
		return this.userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsUserByEmail(String email) {
		return this.userRepository.existsByEmail(email);
	}
}
