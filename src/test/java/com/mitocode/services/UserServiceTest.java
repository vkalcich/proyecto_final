package com.mitocode.services;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mitocode.proyectofinal.dtos.RoleDto;
import com.mitocode.proyectofinal.dtos.UserDto;
import com.mitocode.proyectofinal.entities.Role;
import com.mitocode.proyectofinal.entities.User;
import com.mitocode.proyectofinal.exceptions.ResourceNotFoundException;
import com.mitocode.proyectofinal.repositories.UserRepository;
import com.mitocode.proyectofinal.services.UserService;
import com.mitocode.proyectofinal.services.impl.UserServiceImpl;

public class UserServiceTest {

	private UserRepository userRepository;
	private ModelMapper modelMapper;
	private PasswordEncoder passwordEncoder;
	private UserService userService;
	
	@BeforeEach
	void setUp() {
		userRepository = Mockito.mock(UserRepository.class);
		modelMapper = new ModelMapper();
		passwordEncoder = new BCryptPasswordEncoder();
		userService = new UserServiceImpl(userRepository, modelMapper, passwordEncoder);
	}
	
	@Test
	void findUserByUsernameOrEmailOK () {
		String username = "victor";
		String email = "";
		
		Role role = new Role();
		role.setId(1L);
		role.setName("ROLE_ADMIN");
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		
		User user = new User();
		user.setEmail("vkalcich@gmail.com");
		user.setId(1L);
		user.setPassword("password");
		user.setUsername("victor");
		user.setRoles(roles);
		
		Optional<User> optional = Optional.of(user);
		
		when(this.userRepository.findByUsernameOrEmail(username, email)).thenReturn(optional);
		
		UserDto userDto = this.userService.findUserByUsernameOrEmail(username, email);
		
		Assertions.assertTrue(userDto != null);
		Assertions.assertEquals(userDto.getUsername(), username);
	}
	
	@Test
	void findUserByUsernameOrEmailNotOK () {
		String username = "victor";
		String email = "";
		
		when(this.userRepository.findByUsernameOrEmail(username, email)).thenReturn(Optional.empty());
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> this.userService.findUserByUsernameOrEmail(username, email));
		
		Assertions.assertTrue(thrown != null);
		Assertions.assertEquals(thrown.getMessage(), "User not found with username or email: 'victor-'");
		
	}
	
	@Test
	void createOK() {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(1L);
		roleDto.setName("ROLE_ADMIN");
		
		Set<RoleDto> rolesDto = new HashSet<RoleDto>();
		rolesDto.add(roleDto);
		
		UserDto userDto = new UserDto();
		userDto.setEmail("vkalcich@gmail.com");
		userDto.setId(1L);
		userDto.setPassword("password");
		userDto.setUsername("victor");
		userDto.setRoles(rolesDto);
		
		Role role = new Role();
		role.setId(1L);
		role.setName("ROLE_ADMIN");
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		
		User user = new User();
		user.setEmail("vkalcich@gmail.com");
		user.setId(1L);
		user.setPassword(passwordEncoder.encode("password"));
		user.setUsername("victor");
		user.setRoles(roles);
		
		
		when(this.userRepository.save(user)).thenReturn(user);
		
		UserDto newUser = this.userService.save(userDto);
		
		Assertions.assertTrue(newUser != null);
		Assertions.assertTrue(newUser.getId() != null);
	}
	
	@Test
	void existsUserByUsernameOK() {
		String username = "username";
		when(this.userRepository.existsByUsername(username)).thenReturn(Boolean.TRUE);
		
		Boolean result = this.userService.existsUserByUsername(username);
		Assertions.assertEquals(result, Boolean.TRUE);
	}
	
	@Test
	void existsUserByUsernameNotOK() {
		String username = "username";
		when(this.userRepository.existsByUsername(username)).thenReturn(Boolean.FALSE);
		
		Boolean result = this.userService.existsUserByUsername(username);
		Assertions.assertEquals(result, Boolean.FALSE);
	}
	
	@Test
	void existsUserByEmailOK() {
		String email = "email";
		when(this.userRepository.existsByEmail(email)).thenReturn(Boolean.TRUE);
		
		Boolean result = this.userService.existsUserByEmail(email);
		Assertions.assertEquals(result, Boolean.TRUE);
	}
	
	@Test
	void existsUserByEmailNotOK() {
		String email = "email";
		when(this.userRepository.existsByEmail(email)).thenReturn(Boolean.FALSE);
		
		Boolean result = this.userService.existsUserByEmail(email);
		Assertions.assertEquals(result, Boolean.FALSE);
	}
}