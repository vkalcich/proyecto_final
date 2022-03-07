package com.mitocode.proyectofinal.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	
	@ApiModelProperty(notes = "Id del producto", name = "id")
	private Long id;
	@ApiModelProperty(notes = "Categoria a la que pertenece el producto", name = "categoria")
	private CategoryDto categoryDto;
	@ApiModelProperty(notes = "Nombre del producto", name = "nombre")
	private String name;
	@ApiModelProperty(notes = "Precio del producto", name = "precio")
	private Double price;
	@ApiModelProperty(notes = "Descripcion del producto", name = "descripcion")
	private String description;
	@ApiModelProperty(notes = "Indica si el producto esta habilitado", name = "habilitado")
	private boolean enabled;
	
}
