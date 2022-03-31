package com.mitocode.proyectofinal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mitocode.proyectofinal.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsernameOrEmail(String username, String email);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
}
