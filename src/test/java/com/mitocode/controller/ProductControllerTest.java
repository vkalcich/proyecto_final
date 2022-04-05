package com.mitocode.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mitocode.proyectofinal.controllers.ProductController;
import com.mitocode.proyectofinal.dtos.CategoryDto;
import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.services.ProductService;
import com.mitocode.proyectofinal.services.impl.ProductServiceImpl;
import com.mitocode.proyectofinal.utils.PageResponse;

public class ProductControllerTest {

	private ProductService productService;
	private ProductController productController;
	
	@BeforeEach
	void setUp() {
		productService = Mockito.mock(ProductServiceImpl.class);
		productController = new ProductController(productService);
	}
	
	@Test
	void findById() {
		
		ProductDto productDto = new ProductDto();
		productDto.setId(1L);
		productDto.setName("Prod 1");
		productDto.setDescription("Producto 1");
		productDto.setDueDate(LocalDate.now().plusMonths(2));
		productDto.setEnabled(true);
		productDto.setPrice(1000.00);
		productDto.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		Long id = 1L;
		
		when(productService.findById(id)).thenReturn(productDto);
		
		ResponseEntity<ProductDto> response = this.productController.findById(id);
		
		Assertions.assertTrue(response != null);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void findAll() {
		
		ProductDto productDto = new ProductDto();
		productDto.setName("");
		productDto.setDescription("");
		productDto.setDueDate(LocalDate.now());
		productDto.setEnabled(true);
		productDto.setPrice(0.0);
		
		Integer pageNumber = 0;
		Integer pageSize = 5;
		String sortBy = "id";
		
		PageResponse<ProductDto> products = new PageResponse<>();
		
		ProductDto product = new ProductDto();
		product.setId(1L);
		product.setName("Prod 1");
		product.setDescription("Producto 1");
		product.setDueDate(LocalDate.now().plusMonths(2));
		product.setEnabled(true);
		product.setPrice(500.00);
		product.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		ProductDto product2 = new ProductDto();
		product2.setId(2L);
		product2.setName("Prod 2");
		product2.setDescription("Producto 2");
		product2.setDueDate(LocalDate.now().plusMonths(2));
		product2.setEnabled(true);
		product2.setPrice(1500.00);
		product2.setCategoryDto(new CategoryDto(1L, "Cat 2", "Category 2", true));
		
		List<ProductDto> listaProductos = new ArrayList<ProductDto>();
		listaProductos.add(product);
		listaProductos.add(product2);
		
		products.setContent(listaProductos);
		
		when(productService.findAll(productDto, pageNumber, pageSize, sortBy)).thenReturn(products);
		
		ResponseEntity<PageResponse<ProductDto>> response = this.productController.findAll("", "", 0.0, LocalDate.now(), true, pageNumber, pageSize, sortBy);
		
		Assertions.assertTrue(response != null);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertTrue(response.getBody().getContent() != null);
		Assertions.assertTrue(response.getBody().getContent().size() > 0);
	}
	
	@Test
	void create() {
		ProductDto productDto = new ProductDto();
		productDto.setName("Prod 1");
		productDto.setDescription("Producto 1");
		productDto.setDueDate(LocalDate.now().plusMonths(2));
		productDto.setEnabled(true);
		productDto.setPrice(1000.00);
		productDto.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		ProductDto newProductDto = new ProductDto();
		newProductDto.setId(1L);
		newProductDto.setName("Prod 1");
		newProductDto.setDescription("Producto 1");
		newProductDto.setDueDate(LocalDate.now().plusMonths(2));
		newProductDto.setEnabled(true);
		newProductDto.setPrice(1000.00);
		newProductDto.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		when(this.productService.create(productDto)).thenReturn(newProductDto);
		
		ResponseEntity<ProductDto> response = this.productController.create(productDto);
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		Assertions.assertEquals(response.getBody().getId(), newProductDto.getId());
	}
	
	@Test
	void update() {
		ProductDto productDto = new ProductDto();
		productDto.setId(1L);
		productDto.setName("Prod 1");
		productDto.setDescription("Producto 1 actualizado");
		productDto.setDueDate(LocalDate.now().plusMonths(2));
		productDto.setEnabled(true);
		productDto.setPrice(1000.00);
		productDto.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		ProductDto productUpdateDto = new ProductDto();
		productUpdateDto.setId(1L);
		productUpdateDto.setName("Prod 1");
		productUpdateDto.setDescription("Producto 1 actualizado");
		productUpdateDto.setDueDate(LocalDate.now().plusMonths(2));
		productUpdateDto.setEnabled(true);
		productUpdateDto.setPrice(1000.00);
		productUpdateDto.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		when(this.productService.update(productDto)).thenReturn(productUpdateDto);
		
		ResponseEntity<ProductDto> response = this.productController.update(productDto);
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertTrue(response.getBody() != null);
	}
	
	@Test
	void deleteById() {
		Long id = 1L;
		doNothing().when(productService).deleteById(id);
		HttpStatus status = productController.deleteById(id);
		Assertions.assertTrue(status != null);
		Assertions.assertEquals(status, HttpStatus.NO_CONTENT);
	}
	
	@Test
	void findByCategoryId() {
		Long id = 1L;
		Integer pageNumber = 0;
		Integer pageSize = 10;
		String sortBy = "id";
		
		PageResponse<ProductDto> products = new PageResponse<>();
		
		ProductDto product = new ProductDto();
		product.setId(1L);
		product.setName("Prod 1");
		product.setDescription("Producto 1");
		product.setDueDate(LocalDate.now().plusMonths(2));
		product.setEnabled(true);
		product.setPrice(500.00);
		product.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		ProductDto product2 = new ProductDto();
		product2.setId(2L);
		product2.setName("Prod 2");
		product2.setDescription("Producto 2");
		product2.setDueDate(LocalDate.now().plusMonths(2));
		product2.setEnabled(true);
		product2.setPrice(1500.00);
		product2.setCategoryDto(new CategoryDto(1L, "Cat 2", "Category 2", true));
		
		List<ProductDto> listaProductos = new ArrayList<ProductDto>();
		listaProductos.add(product);
		listaProductos.add(product2);
		
		products.setContent(listaProductos);
		
		
		when(productService.findByCategoryId(id, pageNumber, pageSize, sortBy)).thenReturn(products);
		
		ResponseEntity<PageResponse<ProductDto>> response = productController.findByCategoryId(id, pageNumber, pageSize, sortBy);
		
		Assertions.assertTrue(response != null);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assertions.assertTrue(response.getBody() != null);
		Assertions.assertTrue(response.getBody().getContent() != null);
	}
}
