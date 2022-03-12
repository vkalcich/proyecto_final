package com.mitocode.proyectofinal.services;

import com.mitocode.proyectofinal.dtos.CategoryDto;
import com.mitocode.proyectofinal.utils.PageResponse;

public interface CategoryService {

	CategoryDto create(CategoryDto categoryDto);

	PageResponse<CategoryDto> getAll(Integer pageNo, Integer pageSize, String sortBy);

	CategoryDto getById(Long id);

}
