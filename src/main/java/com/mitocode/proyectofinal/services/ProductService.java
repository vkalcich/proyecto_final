package com.mitocode.proyectofinal.services;

import java.util.List;

import com.mitocode.proyectofinal.dtos.ProductDto;

public interface ProductService {

	ProductDto create(ProductDto productDto);

	ProductDto findById(Long id);

	ProductDto findByName(String name);
	
	List<ProductDto> findByNameIgnoreCase(String name);
}
