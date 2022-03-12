package com.mitocode.proyectofinal.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.proyectofinal.dtos.CategoryDto;
import com.mitocode.proyectofinal.entities.Category;
import com.mitocode.proyectofinal.exceptions.ResourceNotFoundException;
import com.mitocode.proyectofinal.repositories.CategoryRepository;
import com.mitocode.proyectofinal.services.CategoryService;
import com.mitocode.proyectofinal.utils.PageResponse;
import com.mitocode.proyectofinal.utils.PageResponseBuilder;

import lombok.Getter;
import lombok.Setter;

@Service(value = "categoryService")
@Transactional
@Getter
@Setter
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	private PageResponseBuilder<Category, CategoryDto> pageResponseBuilder;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, PageResponseBuilder<Category, CategoryDto> pageResponseBuilder) {
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
		this.pageResponseBuilder = pageResponseBuilder;
	}
	
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category newCategory = this.categoryRepository.save(category);
		return modelMapper.map(newCategory, CategoryDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<CategoryDto> getAll(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
		Page<Category> pagedResult = this.categoryRepository.findAll(paging);
		List<CategoryDto> categories = null;
		PageResponse<CategoryDto> pageResponse = new PageResponse<>();
		
		if (pagedResult.hasContent()) {
			categories = pagedResult.getContent().
					stream().map(entity -> this.modelMapper.map(entity, CategoryDto.class)).collect(Collectors.toList());
			
			pageResponseBuilder.createPageResponse(pagedResult, pageResponse, categories);
		} else {
			throw new ResourceNotFoundException("Category", "all", "");
		}
		
		return pageResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryDto getById(Long id) {
		Category category = this.categoryRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));
		return this.modelMapper.map(category, CategoryDto.class);
	}

}
