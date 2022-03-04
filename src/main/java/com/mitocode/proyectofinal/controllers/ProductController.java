package com.mitocode.proyectofinal.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/products")
@Getter
@Setter
public class ProductController {

	@GetMapping
	public String saludar() {
		return "Hola controller";
	}
}
