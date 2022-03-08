package com.mitocode.proyectofinal.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.proyectofinal.dtos.CategoryDto;
import com.mitocode.proyectofinal.entities.Category;
import com.mitocode.proyectofinal.exceptions.ResourceNotFoundException;
import com.mitocode.proyectofinal.repositories.CategoryRepository;
import com.mitocode.proyectofinal.services.CategoryService;

import lombok.Getter;
import lombok.Setter;

@Service(value = "categoryService")
@Transactional
@Getter
@Setter
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category newCategory = this.categoryRepository.save(category);
		return modelMapper.map(newCategory, CategoryDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryDto> getAll() {
		List<Category> categorias = this.categoryRepository.findAll();
		return categorias.stream().map(entity -> modelMapper.map(entity, CategoryDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryDto getById(Long id) {
		Category category = this.categoryRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));
		return this.modelMapper.map(category, CategoryDto.class);
	}

}
