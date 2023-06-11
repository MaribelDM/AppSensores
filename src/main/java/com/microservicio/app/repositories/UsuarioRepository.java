package com.microservicio.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.app.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Usuario findByUsername(String username);
}
