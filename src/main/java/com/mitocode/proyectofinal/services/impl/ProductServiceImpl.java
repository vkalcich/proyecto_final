package com.mitocode.proyectofinal.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.entities.Product;
import com.mitocode.proyectofinal.exceptions.ResourceNotFoundException;
import com.mitocode.proyectofinal.repositories.ProductRepository;
import com.mitocode.proyectofinal.repositories.specifications.ProductSpecification;
import com.mitocode.proyectofinal.services.ProductService;
import com.mitocode.proyectofinal.utils.PageResponse;
import com.mitocode.proyectofinal.utils.PageResponseBuilder;

import lombok.Getter;
import lombok.Setter;

@Service(value = "productService")
@Transactional
@Getter
@Setter
public class ProductServiceImpl implements ProductService {

	private ModelMapper modelMapper;
	private ProductRepository productRepository;
	private PageResponseBuilder<Product, ProductDto> pageResponseBuilder;
	private ProductSpecification productSpecification;
	
	public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, 
			PageResponseBuilder<Product, ProductDto> pageResponseBuilder, ProductSpecification productSpecification) {
		this.modelMapper = modelMapper;
		modelMapper.typeMap(Product.class, ProductDto.class).addMapping(Product::getCategory, ProductDto::setCategoryDto);
		this.productRepository = productRepository;
		this.pageResponseBuilder = pageResponseBuilder;
		this.productSpecification = productSpecification;
	}

	@Override
	public ProductDto create(ProductDto productDto) {
		Product product = getModelMapper().map(productDto, Product.class);
		Product newProduct = getProductRepository().save(product);
		return this.modelMapper.map(newProduct, ProductDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDto findById(Long id) {
		Product product = this.productRepository.findById(id).
			orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));
		return  this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDto findByName(String name) {
		Product product = this.productRepository.findByName(name).
				orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
			return  this.modelMapper.map(product, ProductDto.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProductDto> findByNameIgnoreCase(String name) {
		List<Product> products = this.productRepository.findByNameIgnoreCase(name).
				orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
			return  products.stream()
	                .map(entity -> this.modelMapper.map(entity, ProductDto.class))
	                .collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProductDto> findByDueDate(LocalDate dueDate) {
		List<Product> products = this.productRepository.findByDueDateLessThan(dueDate).
				orElseThrow(() -> new ResourceNotFoundException("Product", "dueDate", dueDate.toString()));
			return  products.stream()
	                .map(entity -> this.modelMapper.map(entity, ProductDto.class))
	                .collect(Collectors.toList());
	}

	@Override
	public void update(ProductDto productDto) {
		Optional<Product> optionalProduct = this.productRepository.findById(productDto.getId());
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product = this.modelMapper.map(productDto, Product.class);
			this.productRepository.save(product);
		} else {
			throw new ResourceNotFoundException("Product", "id", productDto.getId().toString());
		}
	}

	@Override
	public void deleteById(Long id) {
		Optional<Product> optionalProduct = this.productRepository.findById(id);
		if (optionalProduct.isPresent()) {
			this.productRepository.delete(optionalProduct.get());
		} else {
			throw new ResourceNotFoundException("Product", "id", id.toString());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<ProductDto> findAll(ProductDto productDto, Integer pageNumber, Integer pageSize, String sortBy) {
		
		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		
		Page<Product> page = this.productRepository.findAll(getProductSpecification().getByFilters(productDto), paging);
		
		List<ProductDto> productsDto;
		
		PageResponse<ProductDto> pageResponse = new PageResponse<ProductDto>();
		
		if (page.hasContent()) {
			productsDto = page.getContent().stream()
					        .map(entity -> this.modelMapper.map(entity, ProductDto.class))
					        .collect(Collectors.toList());
			
			getPageResponseBuilder().createPageResponse(page, pageResponse, productsDto);
		} else {
			throw new ResourceNotFoundException("Product", "page", pageNumber.toString());
		}
		
		return pageResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<ProductDto> findByCategoryId(Long id, Integer pageNumber, Integer pageSize, String sortBy) {
		
		Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		
		Page<Product> page = this.productRepository.findByCategoryId(id, paging);
		
		List<ProductDto> products;
		
		PageResponse<ProductDto> pageResponse = new PageResponse<ProductDto>();
		
		if (page.hasContent()) {
			products = page.getContent().stream()
					.map(entity -> this.modelMapper.map(entity, ProductDto.class)).collect(Collectors.toList());
			
			pageResponseBuilder.createPageResponse(page, pageResponse, products);
			
		} else {
		
			throw new ResourceNotFoundException("Product", "categoryID", id.toString());
		}
		return pageResponse;
	}
	
	
	
}
