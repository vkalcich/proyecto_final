package com.mitocode.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mitocode.proyectofinal.dtos.CategoryDto;
import com.mitocode.proyectofinal.entities.Category;
import com.mitocode.proyectofinal.exceptions.ResourceNotFoundException;
import com.mitocode.proyectofinal.repositories.CategoryRepository;
import com.mitocode.proyectofinal.services.CategoryService;
import com.mitocode.proyectofinal.services.impl.CategoryServiceImpl;
import com.mitocode.proyectofinal.utils.PageResponse;
import com.mitocode.proyectofinal.utils.PageResponseBuilder;

public class CategoryServiceTest {
	
	private CategoryService categoryService;
	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	private PageResponseBuilder<Category, CategoryDto> pageResponseBuilder;
	
	@BeforeEach
	public void setUp() {
		categoryRepository = Mockito.mock(CategoryRepository.class);
		modelMapper = new ModelMapper();
		pageResponseBuilder = new PageResponseBuilder<Category, CategoryDto>();
		categoryService = new CategoryServiceImpl(categoryRepository, modelMapper, pageResponseBuilder);
	}
	
	@Test
	public void getByIdReturnsException() {
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> { categoryService.getById(0L); });
		Assertions.assertEquals("Category not found with id: '0'", thrown.getMessage());
	}
	
	@Test
	public void getByIdOK() {
		Category cat = new Category(1L, "producto junit", "producto de prueba", true);
		Optional<Category> o = Optional.of(cat);
		when(categoryRepository.findById(1L)).thenReturn(o);
		CategoryDto cDto = this.categoryService.getById(1L);
		Assertions.assertEquals(cat.getId(), cDto.getId());
		Assertions.assertTrue(cDto != null);
	}
	
	@Test
	public void createOK() {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName("Cat 1");
		categoryDto.setDescription("Categoria 1");
		categoryDto.setEnabled(true);
		
		Category category = this.modelMapper.map(categoryDto, Category.class);
		
		Category newCategory = new Category();
		newCategory.setId(10L);
		newCategory.setDescription(category.getDescription());
		newCategory.setName(category.getName());
		newCategory.setEnabled(category.isEnabled());
		
		when(categoryRepository.save(category)).thenReturn(newCategory);
		
		CategoryDto newCategoryDto = this.categoryService.create(categoryDto);
		
		Assertions.assertTrue(newCategoryDto != null);
		Assertions.assertTrue(newCategoryDto.getId() != null);
	}
	
	@Test
	public void getAllOK() {
		Category newCategory = new Category();
		newCategory.setId(1L);
		newCategory.setDescription("categoria descrpcion");
		newCategory.setName("Categoria nombre");
		newCategory.setEnabled(true);
		
		List<Category> lista = new ArrayList<>();
		lista.add(newCategory);
		
		Page<Category> pagedResult = new PageImpl<Category>(lista);
		Pageable paging = PageRequest.of(0, 5, Sort.by("id"));
		when(this.categoryRepository.findAll(paging)).thenReturn(pagedResult);
		
		PageResponse<CategoryDto> pageResponse = this.categoryService.getAll(0, 5, "id");
		
		Assertions.assertTrue(pageResponse != null);
		Assertions.assertFalse(pageResponse.getContent().isEmpty());
	}
	
	@Test
	public void getAllNotOK() {
		Page<Category> pagedResult = new PageImpl<Category>(new ArrayList<Category>());
		Pageable paging = PageRequest.of(0, 5, Sort.by("id"));
		when(this.categoryRepository.findAll(paging)).thenReturn(pagedResult);
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> this.categoryService.getAll(0, 5, "id"));
		Assertions.assertEquals(thrown.getMessage(), "Category not found with all: ''");
	}
	
}
