package com.mitocode.proyectofinal.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	@ApiModelProperty(notes = "Id de la categoria", name = "id")
	private Long id;
	
	@ApiModelProperty(notes = "Nombre de la categoria", name = "nombre")
	@NotBlank(message="{category.name.blank}")
	@Size(min = 5, message="{category.name.min}")
	private String name;
	
	@ApiModelProperty(notes = "Descripcion de la categoria", name = "descripcion")
	@NotBlank(message = "{category.desctiption.blank}")
	@Size(min = 10, message = "{category.desctiption.min}")
	private String description;
	
	@ApiModelProperty(notes = "Indica si la catergoria esta habilitada", name = "habilitado")
	private boolean enabled;
}
