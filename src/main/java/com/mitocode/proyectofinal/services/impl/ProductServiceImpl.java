package com.mitocode.proyectofinal.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.entities.Product;
import com.mitocode.proyectofinal.exceptions.ResourceNotFoundException;
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
		modelMapper.typeMap(Product.class, ProductDto.class).addMapping(Product::getCategory, ProductDto::setCategoryDto);
		this.productRepository = productRepository;
	}

	@Override
	public ProductDto create(ProductDto productDto) {
		Product product = modelMapper.map(productDto, Product.class);
		this.productRepository.save(product);
		return this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	public ProductDto findById(Long id) {
		Product product = this.productRepository.findById(id).
			orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
		return  this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	public ProductDto findByName(String name) {
		Product product = this.productRepository.findByName(name).
				orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
			return  this.modelMapper.map(product, ProductDto.class);
	}
	
	@Override
	public List<ProductDto> findByNameIgnoreCase(String name) {
		List<Product> products = this.productRepository.findByNameIgnoreCase(name).
				orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
			return  products.stream()
	                .map(entity -> this.modelMapper.map(entity, ProductDto.class))
	                .collect(Collectors.toList());
	}
	
}
