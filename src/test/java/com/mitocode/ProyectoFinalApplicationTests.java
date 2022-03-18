package com.mitocode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mitocode.proyectofinal.ProyectoFinalApplication;
import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.services.ProductService;


@SpringBootTest(classes = {ProyectoFinalApplication.class})
class ProyectoFinalApplicationTests {

	@Autowired
	private ProductService productService;
	
	@Test
	void prueba() {
		ProductDto productDto = productService.findById(1L);
		Assert. notNull(productDto);
		Assertions.assertEquals(1L, productDto.getId());
		Assertions.assertNotNull(productDto.getCategoryDto());
	}

}
