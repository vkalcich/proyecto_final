package com.mitocode.proyectofinal.dtos;

import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@ApiModelProperty(notes = "id del usuario", name = "id")
	private Long id;
	@ApiModelProperty(notes = "Username del usuario", name = "nombre de usuario")
	private String username;
	@ApiModelProperty(notes = "Email del usuario", name = "email")
	private String email;
	@ApiModelProperty(notes = "Password del usuario", name = "contraseña")
	private String password;
	@ApiModelProperty(notes = "Lista de roles del usuario", name = "roles")
	private Set<RoleDto> roles;
}
