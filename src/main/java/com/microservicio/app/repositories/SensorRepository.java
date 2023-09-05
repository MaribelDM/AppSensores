package com.microservicio.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.app.entities.Sensor;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Integer> {
	
	List<Sensor> findByIdUsuarioAndTipo(Integer usuario, String tipo);
	
	Sensor findByNombreAndTipo(String nombre, String tipo);
	
	Sensor findByIdAndTipo(Integer id, String tipo);
	
	Sensor findByNombreAndTipoAndIdUsuario(String nombre, String tipo, Integer idUsuario);

}
