package com.mitocode.proyectofinal.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

	@ApiModelProperty(notes = "nombre de usuario a crear", name = "nombre de usuario")
	private String username;
	
	@ApiModelProperty(notes = "email de usuario a crear", name = "email")
	private String email;
	
	@ApiModelProperty(notes = "Contraseña del usuario a crear", name = "contraseña")
	private String password;
	
}
