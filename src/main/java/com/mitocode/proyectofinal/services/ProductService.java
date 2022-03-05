package com.mitocode.proyectofinal.services;

import com.mitocode.proyectofinal.dtos.ProductDto;

public interface ProductService {

	String saludar();
	
	ProductDto create(ProductDto productDto);
}
