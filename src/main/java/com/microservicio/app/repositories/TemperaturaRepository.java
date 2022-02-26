package com.microservicio.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.microservicio.app.entities.Temperatura;

public interface TemperaturaRepository extends CrudRepository<Temperatura, Long>{

	List<Temperatura> findAll();

}
