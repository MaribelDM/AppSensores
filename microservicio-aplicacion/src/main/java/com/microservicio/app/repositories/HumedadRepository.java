package com.microservicio.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.microservicio.app.entities.Humedad;

public interface HumedadRepository extends CrudRepository<Humedad, Long> {
	
	List<Humedad> findAllByOrderByFecha();

}
