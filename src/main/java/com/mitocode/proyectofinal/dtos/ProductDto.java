package com.mitocode.proyectofinal.dtos;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotBlank(message = "{product.name.blank}")
	@Size(min = 5, message = "{product.name.min}")
	private String name;
	
	@ApiModelProperty(notes = "Precio del producto", name = "precio")
	@NotNull(message = "{product.price.empty}")
	private Double price;
	
	@ApiModelProperty(notes = "Descripcion del producto", name = "descripcion")
	@NotBlank(message = "{product.description.blank}")
	@Size(min = 10, message = "{product.description.min}")
	private String description;
	
	@ApiModelProperty(notes = "Indica si el producto esta vencido", name = "fecha vencimiento")
	@Future(message = "{product.dueDate.future}")
	private LocalDate dueDate;
	
	@ApiModelProperty(notes = "Indica si el producto esta habilitado", name = "habilitado")
	private boolean enabled;
	
}
