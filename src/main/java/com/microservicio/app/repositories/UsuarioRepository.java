package com.microservicio.app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.microservicio.app.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	Usuario findByName(String Name);
}
