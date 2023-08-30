package com.microservicio.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.app.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Usuario findByUsername(String username);
	
	List<Usuario> findByRole(String role);
}
