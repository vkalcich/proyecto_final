package com.mitocode.proyectofinal.services;

import java.time.LocalDate;
import java.util.List;

import com.mitocode.proyectofinal.dtos.ProductDto;

public interface ProductService {

	ProductDto create(ProductDto productDto);

	ProductDto findById(Long id);
	
	List<ProductDto> findAll();

	ProductDto findByName(String name);
	
	List<ProductDto> findByNameIgnoreCase(String name);

	List<ProductDto> findByDueDate(LocalDate dueDate);

	void update(ProductDto productDto);

	void deleteById(Long id);

	List<ProductDto> findByCategoryId(Long id);
}
