package com.mitocode.proyectofinal.dtos;

import lombok.NoArgsConstructor;

import lombok.Setter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDto {

	@ApiModelProperty(notes = "id del rol", name = "id")
	private Long id;
	
	@ApiModelProperty(notes = "Nombre del rol", name = "nombre")
	private String name;
}
