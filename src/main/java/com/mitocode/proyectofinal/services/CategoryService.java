package com.mitocode.proyectofinal.services;

import java.util.List;

import com.mitocode.proyectofinal.dtos.CategoryDto;

public interface CategoryService {

	CategoryDto create(CategoryDto categoryDto);

	List<CategoryDto> getAll();

	CategoryDto getById(Long id);

}
