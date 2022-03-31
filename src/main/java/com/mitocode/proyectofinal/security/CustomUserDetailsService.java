package com.mitocode.proyectofinal.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mitocode.proyectofinal.dtos.RoleDto;
import com.mitocode.proyectofinal.dtos.UserDto;
import com.mitocode.proyectofinal.services.UserService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		UserDto userDto = userService.findUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		return new User(userDto.getUsername(), userDto.getPassword(), mapRolesToAuthorities(userDto.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<RoleDto> roles) {
		return roles.stream()
				.map(role->new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}

}
