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
public class SignInDto {

	@ApiModelProperty(notes = "Nombre de usuario o email del usuario", name = "nombre de usuario o email")
	private String usernameOrEmail;
	
	@ApiModelProperty(notes = "Contraseña del usuario", name = "contraseña")
	private String password;
}
