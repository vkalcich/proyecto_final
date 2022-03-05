package com.mitocode.proyectofinal.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.services.ProductService;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/products")
@Getter
@Setter
public class ProductController {
	
	@Qualifier(value = "productService")
	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<String> saludar() {
		String saludo = productService.saludar();
		return new ResponseEntity<String>(saludo, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Crea un producto")
	@PostMapping
	public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto){
		ProductDto newProductDto = this.productService.create(productDto);
		return new ResponseEntity<ProductDto>(newProductDto, HttpStatus.CREATED);
	}
}
