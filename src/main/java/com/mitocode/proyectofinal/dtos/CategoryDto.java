package com.mitocode.proyectofinal.dtos;

import lombok.Getter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	@ApiModelProperty(notes = "Id de la categoria", name = "id")
	private Long id;
	@ApiModelProperty(notes = "Nombre de la categoria", name = "nombre")
	private String name;
	@ApiModelProperty(notes = "Descripcion de la categoria", name = "descripcion")
	private String description;
	@ApiModelProperty(notes = "Indica si la catergoria esta habilitada", name = "habilitado")
	private boolean enabled;
}
