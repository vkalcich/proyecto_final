package com.mitocode.proyectofinal.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long id;
	
	private CategoryDto categoryDto;
	
	private String name;
	
	private Double price;
	
	private String description;
	
	private boolean enabled;
	
}
