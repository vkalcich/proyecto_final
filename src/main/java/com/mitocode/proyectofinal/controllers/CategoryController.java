package com.mitocode.proyectofinal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.proyectofinal.dtos.CategoryDto;
import com.mitocode.proyectofinal.services.CategoryService;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/categories")
@Getter
@Setter
public class CategoryController {
	
	@Qualifier(value = "categoryService")
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@ApiOperation(value = "metodo para crear una categoria")
	@PostMapping
	public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto) {
		CategoryDto newCategoryDto = this.categoryService.create(categoryDto);
		return new ResponseEntity<CategoryDto>(newCategoryDto, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Obtiene todas las categorias")
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAll() {
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAll(), HttpStatus.OK);
	}

	@ApiOperation(value = "Obtiene una categoria filtrando por id")
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
		CategoryDto category = this.categoryService.getById(id);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}
}
