package com.microservicio.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.microservicio.app.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	List<Usuario> findByNombre(String nombre);

	
}
