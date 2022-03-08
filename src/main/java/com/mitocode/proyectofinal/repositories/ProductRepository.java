package com.mitocode.proyectofinal.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mitocode.proyectofinal.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findByName(String name);
	
	@Query("SELECT p from Product p where LOWER(name) = LOWER(?1)")
	Optional<List<Product>> findByNameIgnoreCase(@Param("name") String name);
	
	Optional<List<Product>> findByDueDateLessThan(@Param("dueDate") LocalDate dueDate);
}
