package com.mitocode.services;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.data.jpa.domain.Specification;

import com.mitocode.proyectofinal.dtos.CategoryDto;
import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.entities.Category;
import com.mitocode.proyectofinal.entities.Product;
import com.mitocode.proyectofinal.exceptions.ResourceNotFoundException;
import com.mitocode.proyectofinal.repositories.ProductRepository;
import com.mitocode.proyectofinal.repositories.specifications.ProductSpecification;
import com.mitocode.proyectofinal.services.ProductService;
import com.mitocode.proyectofinal.services.impl.ProductServiceImpl;
import com.mitocode.proyectofinal.utils.PageResponse;
import com.mitocode.proyectofinal.utils.PageResponseBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductServiceTest {

	private ModelMapper modelMapper;
	private ProductRepository productRepository;
	private PageResponseBuilder<Product, ProductDto> pageResponseBuilder;
	private ProductSpecification productSpecification;
	private ProductService productService;
	Page<Product> page;
	
	@BeforeEach
	public void setUp() {
		modelMapper = new ModelMapper();
		modelMapper.typeMap(Product.class, ProductDto.class).addMapping(Product::getCategory, ProductDto::setCategoryDto);
		productRepository = Mockito.mock(ProductRepository.class);
		pageResponseBuilder = new PageResponseBuilder<Product, ProductDto>();
		productSpecification = Mockito.mock(ProductSpecification.class);
		productService = new ProductServiceImpl(modelMapper, productRepository, pageResponseBuilder, productSpecification);
		
		
	}
	
	@Test
	public void createOK() {
		CategoryDto categoryDto = new CategoryDto(1L, "Cat 1", "Category 1", true);
		LocalDate fechaFencimiento = LocalDate.now().plusMonths(1).plusYears(1);
		ProductDto productDto = new ProductDto(null, categoryDto, "Prod 1", 500.00, "Producto 1", fechaFencimiento, true);
		
		Product product = modelMapper.map(productDto, Product.class);
		
		Product newProduct = new Product(1L, new Category(1L, "Cat 1", "Category 1", true), "Prod 1", 500.00, "Producto 1", fechaFencimiento, true);
		when(productRepository.save(product)).thenReturn(newProduct);
		
		ProductDto productCreated = this.productService.create(productDto);
		Assertions.assertTrue(productCreated != null);
		Assertions.assertTrue(productCreated.getId() != null);
	}
	
	@Test
	public void findByIdOK() {
		Long id = 1L;
		Category category = new Category(1L, "Cat 1", "Category 1", true);
		LocalDate fechaFencimiento = LocalDate.now().plusMonths(1).plusYears(1);
		Product product = new Product(1L, category, "Prod 1", 500.00, "Producto 1", fechaFencimiento, true);
		Optional<Product> optionalProduct = Optional.of(product);
		when(this.productRepository.findById(id)).thenReturn(optionalProduct);
		ProductDto productObtained = this.productService.findById(id);
		
		Assertions.assertTrue(productObtained != null);
		
	}
	
	@Test
	public void findByIdNotOK() {
		Long id = 10L;
		when(this.productRepository.findById(id)).thenReturn(Optional.empty());
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> this.productService.findById(id));
		Assertions.assertTrue(thrown.getMessage().equals("Product not found with id: '10'"));
	}
	
	@Test
	public void findByNameOK() {
		String name = "producto";
		Category category = new Category(1L, "Cat 1", "Category 1", true);
		LocalDate fechaFencimiento = LocalDate.now().plusMonths(1).plusYears(1);
		Product product = new Product(1L, category, "Prod 1", 500.00, "Producto 1", fechaFencimiento, true);
		Optional<Product> optionalProduct = Optional.of(product);
		when(this.productRepository.findByName(name)).thenReturn(optionalProduct);
		ProductDto productObtained = this.productService.findByName(name);
		
		Assertions.assertTrue(productObtained != null);
	}
	
	@Test
	public void findByNameNotOK() {
		String name = "producto";
		when(this.productRepository.findByName(name)).thenReturn(Optional.empty());
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> this.productService.findByName(name));
		Assertions.assertTrue(thrown.getMessage().equals("Product not found with name: 'producto'"));
	}
	
	@Test
	public void findByNameIgnoreCaseOK() {
		String name = "producto";
		Category category = new Category(1L, "Cat 1", "Category 1", true);
		LocalDate fechaFencimiento = LocalDate.now().plusMonths(1).plusYears(1);
		Product product = new Product(1L, category, "Prod 1", 500.00, "Producto 1", fechaFencimiento, true);
		List<Product> listaProductos = new ArrayList<Product>();
		listaProductos.add(product);
		Optional<List<Product>> listOptionalProduct = Optional.of(listaProductos);  
				
		when(this.productRepository.findByNameIgnoreCase(name)).thenReturn(listOptionalProduct);
		List<ProductDto> productsObtained = this.productService.findByNameIgnoreCase(name);
		
		Assertions.assertTrue(productsObtained != null);
		Assertions.assertTrue(productsObtained.size() > 0);
	}
	
	@Test
	public void findByNameIgnoreCaseNotOK() {
		String name = "producto";
		when(this.productRepository.findByNameIgnoreCase(name)).thenReturn(Optional.empty());
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> this.productService.findByNameIgnoreCase(name));
		Assertions.assertTrue(thrown.getMessage().equals("Product not found with name: 'producto'"));
	}
	
	@Test
	public void findBydueDateOK() {
		LocalDate dueDate = LocalDate.now();
		Category category = new Category(1L, "Cat 1", "Category 1", true);
		LocalDate fechaFencimiento = LocalDate.now().plusMonths(1).plusYears(1);
		Product product = new Product(1L, category, "Prod 1", 500.00, "Producto 1", fechaFencimiento, true);
		List<Product> listaProductos = new ArrayList<Product>();
		listaProductos.add(product);
		Optional<List<Product>> listOptionalProduct = Optional.of(listaProductos);  
				
		when(this.productRepository.findByDueDateLessThan(dueDate)).thenReturn(listOptionalProduct);
		List<ProductDto> productsObtained = this.productService.findByDueDate(dueDate);
		
		Assertions.assertTrue(productsObtained != null);
		Assertions.assertTrue(productsObtained.size() > 0);
	}
	
	@Test
	public void findBydueDateNotOK() {
		LocalDate dueDate = LocalDate.now();
		when(this.productRepository.findByDueDateLessThan(dueDate)).thenReturn(Optional.empty());
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> this.productService.findByDueDate(dueDate));
		Assertions.assertTrue(thrown.getMessage().equals("Product not found with dueDate: '" + dueDate.toString() +"'"));
	}
	
	@Test
	public void updateOK() {
		ProductDto productDto = new ProductDto();
		productDto.setId(1L);
		productDto.setName("Prod 1");
		productDto.setDescription("Producto 1");
		productDto.setDueDate(LocalDate.now().plusMonths(2));
		productDto.setEnabled(true);
		productDto.setPrice(1000.00);
		productDto.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		Product product = new Product();
		product.setId(1L);
		product.setName("Prod 1");
		product.setDescription("Producto 1");
		product.setDueDate(LocalDate.now().plusMonths(2));
		product.setEnabled(true);
		product.setPrice(500.00);
		product.setCategory(new Category(1L, "Cat 1", "Category 1", true));
		
		Product savedProduct = new Product();
		savedProduct.setId(1L);
		savedProduct.setName("Prod 1");
		savedProduct.setDescription("Producto 1");
		savedProduct.setDueDate(LocalDate.now().plusMonths(2));
		savedProduct.setEnabled(true);
		savedProduct.setPrice(1000.00);
		savedProduct.setCategory(new Category(1L, "Cat 1", "Category 1", true));
		
		Optional<Product> optional = Optional.of(product);
		
		when(this.productRepository.findById(productDto.getId())).thenReturn(optional);
		
		when(this.productRepository.save(savedProduct)).thenReturn(savedProduct);
		
		ProductDto updatedProduct = this.productService.update(productDto);
		
		Assertions.assertTrue(updatedProduct != null);
		Assertions.assertTrue(updatedProduct.getPrice().equals(productDto.getPrice()));
	}
	
	@Test
	public void updateNotOK() {
		ProductDto productDto = new ProductDto();
		productDto.setId(1L);
		productDto.setName("Prod 1");
		productDto.setDescription("Producto 1");
		productDto.setDueDate(LocalDate.now().plusMonths(2));
		productDto.setEnabled(true);
		productDto.setPrice(1000.00);
		productDto.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		Optional<Product> optional = Optional.empty();
		when(this.productRepository.findById(productDto.getId())).thenReturn(optional);
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> this.productService.update(productDto));
		Assertions.assertTrue(thrown.getMessage().equals("Product not found with id: '1'"));
	}
	
	@Test
	public void deleteByIdOK() {
		
		Product product = new Product();
		product.setId(1L);
		product.setName("Prod 1");
		product.setDescription("Producto 1");
		product.setDueDate(LocalDate.now().plusMonths(2));
		product.setEnabled(true);
		product.setPrice(500.00);
		product.setCategory(new Category(1L, "Cat 1", "Category 1", true));
		Long id = 1L;
		
		Optional<Product> optional = Optional.of(product);
		
		when(this.productRepository.findById(id)).thenReturn(optional);
		
		Assertions.assertDoesNotThrow(() -> this.productService.deleteById(id));
		
		this.productService.deleteById(id);
	}
	
	@Test
	public void deleteByIdNotOK() {
		
		Long id = 1L;
		
		Optional<Product> optional = Optional.empty();
		
		when(this.productRepository.findById(id)).thenReturn(optional);
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> this.productService.deleteById(id));
		Assertions.assertEquals(thrown.getMessage(), "Product not found with id: '1'");
		
		
		//this.productService.deleteById(id);
	}
	
	@Test
	public void findByCategoryIdOK() {
		Long id = 1L;
		Pageable paging = PageRequest.of(0, 5, Sort.by("id"));
		
		Product product = new Product();
		product.setId(1L);
		product.setName("Prod 1");
		product.setDescription("Producto 1");
		product.setDueDate(LocalDate.now().plusMonths(2));
		product.setEnabled(true);
		product.setPrice(500.00);
		product.setCategory(new Category(1L, "Cat 1", "Category 1", true));
		
		Product product2 = new Product();
		product2.setId(2L);
		product2.setName("Prod 2");
		product2.setDescription("Producto 2");
		product2.setDueDate(LocalDate.now().plusMonths(2));
		product2.setEnabled(true);
		product2.setPrice(1500.00);
		product2.setCategory(new Category(1L, "Cat 2", "Category 2", true));
		
		List<Product> listaProductos = new ArrayList<Product>();
		listaProductos.add(product);
		listaProductos.add(product2);
		
		page = new PageImpl<Product>(listaProductos);
		
		when(this.productRepository.findByCategoryId(id, paging)).thenReturn(page);
		
		PageResponse<ProductDto> response = this.productService.findByCategoryId(id, 0, 5, "id");
		Assertions.assertTrue(response != null);
		Assertions.assertTrue(response.getContent().size() == 2);
	}
	
	@Test
	public void findByCategoryIdNotOK() {
		Long id = 1L;
		Pageable paging = PageRequest.of(0, 5, Sort.by("id"));

		page = new PageImpl<Product>(new ArrayList<Product>());
		
		when(this.productRepository.findByCategoryId(id, paging)).thenReturn(page);
		
		ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> this.productService.findByCategoryId(id, 0 , 5, "id"));
		Assertions.assertEquals(thrown.getMessage(), "Product not found with categoryID: '1'");
	}
	
	@Test
	public void findAllOK() {
		Pageable paging = PageRequest.of(0, 5, Sort.by("id"));
		
		ProductDto productDto = new ProductDto();
		productDto.setId(1L);
		productDto.setName("Prod 1");
		productDto.setDescription("Producto 1");
		productDto.setDueDate(LocalDate.now().plusMonths(2));
		productDto.setEnabled(true);
		productDto.setPrice(1000.00);
		productDto.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		Product product = new Product();
		product.setId(1L);
		product.setName("Prod 1");
		product.setDescription("Producto 1");
		product.setDueDate(LocalDate.now().plusMonths(2));
		product.setEnabled(true);
		product.setPrice(500.00);
		product.setCategory(new Category(1L, "Cat 1", "Category 1", true));
		
		Product product2 = new Product();
		product2.setId(2L);
		product2.setName("Prod 2");
		product2.setDescription("Producto 2");
		product2.setDueDate(LocalDate.now().plusMonths(2));
		product2.setEnabled(true);
		product2.setPrice(1500.00);
		product2.setCategory(new Category(1L, "Cat 2", "Category 2", true));
		
		List<Product> listaProductos = new ArrayList<Product>();
		listaProductos.add(product);
		listaProductos.add(product2);
		
		Page<Product> page = new PageImpl<Product>(listaProductos);
		
		ProductDto productDtoResponse = new ProductDto();
		productDtoResponse.setId(1L);
		productDtoResponse.setName("Prod 1");
		productDtoResponse.setDescription("Producto 1");
		productDtoResponse.setDueDate(LocalDate.now().plusMonths(2));
		productDtoResponse.setEnabled(true);
		productDtoResponse.setPrice(500.00);
		productDtoResponse.setCategoryDto(new CategoryDto(1L, "Cat 1", "Category 1", true));
		
		ProductDto productDtoResponse2 = new ProductDto();
		productDtoResponse2.setId(2L);
		productDtoResponse2.setName("Prod 2");
		productDtoResponse2.setDescription("Producto 2");
		productDtoResponse2.setDueDate(LocalDate.now().plusMonths(2));
		productDtoResponse2.setEnabled(true);
		productDtoResponse2.setPrice(1500.00);
		productDtoResponse2.setCategoryDto(new CategoryDto(1L, "Cat 2", "Category 2", true));
		
		List<ProductDto> listaProductosDto = new ArrayList<ProductDto>();
		listaProductosDto.add(productDtoResponse);
		listaProductosDto.add(productDtoResponse2);
		
		PageResponse<ProductDto> pageResponseParameter = new PageResponse<ProductDto>();
		PageResponse<ProductDto> pageResponse = new PageResponse<ProductDto>();
		pageResponse.setContent(listaProductosDto);
		pageResponse.setLast(true);
		pageResponse.setPageNo(0);
		pageResponse.setPageSize(2);
		pageResponse.setTotalElements(2);
		pageResponse.setTotalPages(1);
		
		List<ProductDto> productsDto = new ArrayList<ProductDto>();
		
		when(this.productRepository.findAll(getProductSpecification().getByFilters(productDto), paging)).thenReturn(page);
		
		PageResponse<ProductDto> finalPageResponse = this.productService.findAll(productDto, 0, 5, "id");
		
		Assertions.assertTrue(finalPageResponse != null);
		Assertions.assertTrue(finalPageResponse.getContent().size() > 0);
	}
}
