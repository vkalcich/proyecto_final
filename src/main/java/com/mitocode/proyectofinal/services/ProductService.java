package com.mitocode.proyectofinal.services;

import java.time.LocalDate;
import java.util.List;

import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.utils.PageResponse;

public interface ProductService {

	ProductDto create(ProductDto productDto);

	ProductDto findById(Long id);
	
	PageResponse<ProductDto> findAll(ProductDto productDto, Integer pageNumber, Integer pageSize, String sortBy);

	ProductDto findByName(String name);
	
	List<ProductDto> findByNameIgnoreCase(String name);

	List<ProductDto> findByDueDate(LocalDate dueDate);

	void update(ProductDto productDto);

	void deleteById(Long id);

	PageResponse<ProductDto> findByCategoryId(Long id, Integer pageNumber, Integer pageSize, String sortBy);
}
