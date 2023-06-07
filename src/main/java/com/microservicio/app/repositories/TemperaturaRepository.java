package com.microservicio.app.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.microservicio.app.entities.Temperatura;

public interface TemperaturaRepository extends CrudRepository<Temperatura, Long>{

	List<Temperatura> findByOrderByFecha();
	
	List<Temperatura> findByFechaBetween(Timestamp startDate, Timestamp endDate);
	
	List<Temperatura> findByFecha(Timestamp startDate);

}
