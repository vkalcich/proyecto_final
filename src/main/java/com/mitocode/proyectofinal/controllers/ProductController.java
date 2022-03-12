package com.mitocode.proyectofinal.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.services.ProductService;
import com.mitocode.proyectofinal.utils.PageResponse;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/products")
@Getter
@Setter
@PropertySource("classpath:message.properties")
public class ProductController {
	
	@Qualifier(value = "productService")
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@ApiOperation(value = "Obtiene un producto filtrando por id")
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> findById(Long id) {
		ProductDto product = productService.findById(id);
		return new ResponseEntity<ProductDto>(product, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtiene todos los productos")
	@GetMapping()
	public ResponseEntity<PageResponse<ProductDto>> findAll(@RequestParam(defaultValue = "0")Integer pageNumber,
													@RequestParam(defaultValue = "10")Integer pageSize,
													@RequestParam(defaultValue = "id")String sortBy) {
		PageResponse<ProductDto> products = productService.findAll(pageNumber, pageSize, sortBy);
		return new ResponseEntity<PageResponse<ProductDto>>(products, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Crea un producto")
	@PostMapping
	public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto){
		ProductDto newProductDto = this.productService.create(productDto);
		return new ResponseEntity<ProductDto>(newProductDto, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Obtiene un producto filtrando por nombre ignorando mayusculas y minusculas")
	@GetMapping("/{name}")
	public ResponseEntity<List<ProductDto>> findByNameIgnoreCase(String name) {
		List<ProductDto> products = productService.findByNameIgnoreCase(name);
		return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtiene productos vencidos")
	@GetMapping("/{dueDate}")
	public ResponseEntity<List<ProductDto>> findBydueDate(String dueDate) {
		List<ProductDto> products;
		try {
			 products = productService.findByDueDate(LocalDate.parse(dueDate));
		} catch(DateTimeParseException e) {
			return new ResponseEntity<List<ProductDto>>(new ArrayList<ProductDto>(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
	}
	
	@Value("${update.message}")
	private String updateMessage;
	
	@ApiOperation(value = "Actualiza un producto")
	@PutMapping
	public ResponseEntity<String> update(@RequestBody ProductDto productDto){
		this.productService.update(productDto);
		return new ResponseEntity<String>(updateMessage, HttpStatus.OK);
	}
	
	@Value("${delete.message}")
	private String deleteMessage;
	
	@ApiOperation(value = "Elimina un producto filtrando por id")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(Long id) {
		productService.deleteById(id);
		return new ResponseEntity<String>(deleteMessage, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtiene una lista de productos filtrando por id de categoria")
	@GetMapping("/{categoryId}")
	public ResponseEntity<PageResponse<ProductDto>> findByCategoryId(Long categoryId, @RequestParam(defaultValue = "0")Integer pageNumber,
																			  @RequestParam(defaultValue = "10")Integer pageSize,
																			  @RequestParam(defaultValue = "id")String sortBy) {
		PageResponse<ProductDto> products = productService.findByCategoryId(categoryId, pageNumber, pageSize, sortBy);
		return new ResponseEntity<PageResponse<ProductDto>>(products, HttpStatus.OK);
	}
	
}
