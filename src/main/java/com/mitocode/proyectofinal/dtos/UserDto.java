package com.mitocode.proyectofinal.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long id;
	private String username;
	private String email;
	private String password;
	private Set<RoleDto> roles;
}
