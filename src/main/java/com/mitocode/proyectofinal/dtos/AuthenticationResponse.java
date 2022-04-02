package com.mitocode.proyectofinal.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationResponse {
	
	@ApiModelProperty(notes = "token enviado", name = "token")
	private String token;
	
	@ApiModelProperty(notes = "Tipo de token enviado", name = "tipo de token")
	private String tokenType="Bearer";
	
	public AuthenticationResponse(String token) {
		this.token = token;
	}
	

}
