package com.mitocode.proyectofinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mitocode.proyectofinal.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
