package com.mitocode.services;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import com.mitocode.proyectofinal.dtos.CategoryDto;
import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.entities.Category;
import com.mitocode.proyectofinal.entities.Product;
import com.mitocode.proyectofinal.repositories.ProductRepository;
import com.mitocode.proyectofinal.repositories.specifications.ProductSpecification;
import com.mitocode.proyectofinal.services.ProductService;
import com.mitocode.proyectofinal.services.impl.ProductServiceImpl;
import com.mitocode.proyectofinal.utils.PageResponseBuilder;

public class ProductServiceTest {

	private ModelMapper modelMapper;
	private ProductRepository productRepository;
	private PageResponseBuilder<Product, ProductDto> pageResponseBuilder;
	private ProductSpecification productSpecification;
	private ProductService productService;
	
	@BeforeEach
	public void setUp() {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(Product.class, ProductDto.class).addMapping(Product::getCategory, ProductDto::setCategoryDto);
		productRepository = Mockito.mock(ProductRepository.class);
		pageResponseBuilder = new PageResponseBuilder<>();
		productSpecification = new ProductSpecification();
		productService = new ProductServiceImpl(modelMapper, productRepository, pageResponseBuilder, productSpecification);
	}
	
	@Test
	public void createOK() {
		CategoryDto categoryDto = new CategoryDto(1L, "Cat 1", "Category 1", true);
		LocalDate fechaFencimiento = LocalDate.now().plusMonths(1).plusYears(1);
		System.out.println(fechaFencimiento);
		ProductDto productDto = new ProductDto(null, categoryDto, "Prod 1", 500.00, "Producto 1", fechaFencimiento, true);
		
		Product product = modelMapper.map(productDto, Product.class);
		
		Product newProduct = new Product(1L, new Category(1L, "Cat 1", "Category 1", true), "Prod 1", 500.00, "Producto 1", fechaFencimiento, true);
		when(productRepository.save(product)).thenReturn(newProduct);
		
		ProductDto productCreated = this.productService.create(productDto);
		Assertions.assertTrue(productCreated != null);
		Assertions.assertTrue(productCreated.getId() != null);
		
	}
}
