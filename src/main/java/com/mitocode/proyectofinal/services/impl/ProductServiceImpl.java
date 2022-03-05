package com.mitocode.proyectofinal.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.entities.Product;
import com.mitocode.proyectofinal.repositories.ProductRepository;
import com.mitocode.proyectofinal.services.ProductService;

import lombok.Getter;
import lombok.Setter;

@Service(value = "productService")
@Transactional
@Getter
@Setter
public class ProductServiceImpl implements ProductService {

	private ModelMapper modelMapper;
	private ProductRepository productRepository;
	
	public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository) {
		this.modelMapper = modelMapper;
		this.productRepository = productRepository;
	}
	
	@Override
	public String saludar() {
		return 	"Hola soy el servicio de productos";
	}

	@Override
	public ProductDto create(ProductDto productDto) {
		Product product = modelMapper.map(productDto, Product.class);
		this.productRepository.save(product);
		return modelMapper.map(product, ProductDto.class);
	}
	
}
