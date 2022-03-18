package com.mitocode.proyectofinal.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Validated
public class ProductController {
	
	@Qualifier(value = "productService")
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@ApiOperation(value = "Obtiene un producto filtrando por id")
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable @Min(1) Long id) {
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
	public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto){
		ProductDto newProductDto = this.productService.create(productDto);
		return new ResponseEntity<ProductDto>(newProductDto, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Obtiene un producto filtrando por nombre ignorando mayusculas y minusculas")
	@GetMapping("/{name}")
	public ResponseEntity<List<ProductDto>> findByNameIgnoreCase(@Size(min = 4) @RequestParam String name) {
		List<ProductDto> products = productService.findByNameIgnoreCase(name);
		return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Obtiene productos vencidos")
	@GetMapping("/{dueDate}")
	public ResponseEntity<List<ProductDto>> findBydueDate(@NotNull @RequestParam String dueDate) {
		List<ProductDto> products;
		try {
			 products = productService.findByDueDate(LocalDate.parse(dueDate));
		} catch(DateTimeParseException e) {
			return new ResponseEntity<List<ProductDto>>(new ArrayList<ProductDto>(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza un producto")
	@PutMapping
	public ResponseEntity<ProductDto> update(@Valid @RequestBody ProductDto productDto){
		this.productService.update(productDto);
		return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Elimina un producto filtrando por id")
	@DeleteMapping("/{id}")
	public HttpStatus deleteById(Long id) {
		productService.deleteById(id);
		return HttpStatus.NO_CONTENT;
	}
	
	@ApiOperation(value = "Obtiene una lista de productos filtrando por id de categoria")
	@GetMapping("/category/{id}")
	public ResponseEntity<PageResponse<ProductDto>> findByCategoryId(Long id, @RequestParam(defaultValue = "0")Integer pageNumber,
																			  @RequestParam(defaultValue = "10")Integer pageSize,
																			  @RequestParam(defaultValue = "id")String sortBy) {
		PageResponse<ProductDto> products = productService.findByCategoryId(id, pageNumber, pageSize, sortBy);
		return new ResponseEntity<PageResponse<ProductDto>>(products, HttpStatus.OK);
	}
	
}
