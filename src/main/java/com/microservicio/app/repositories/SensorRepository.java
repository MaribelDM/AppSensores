package com.microservicio.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.microservicio.app.entities.Sensor;

public interface SensorRepository extends CrudRepository<Sensor, Integer> {
	
	List<Sensor> findByIdUsuarioAndTipo(Integer usuario, String tipo);
	
	Sensor findByNombreAndTipo(String nombre, String tipo);

}
