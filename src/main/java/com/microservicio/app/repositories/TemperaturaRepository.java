package com.microservicio.app.repositories;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.microservicio.app.entities.Temperatura;

public interface TemperaturaRepository extends CrudRepository<Temperatura, Integer>{

	List<Temperatura> findByOrderByFecha();
	
	List<Temperatura> findByFechaBetween(Timestamp startDate, Timestamp endDate);
	
	List<Temperatura> findByFecha(Timestamp startDate);
	
	List<Temperatura> findByIdSensorOrderByFecha(Integer idSensor);

//	List<Temperatura> findByFechaBetween(Integer idSensor, LocalDateTime startDateConvert, LocalDateTime endDateConvert);

	List<Temperatura> findByIdSensorAndFechaBetween(Integer id, LocalDateTime startDateConvert,
			LocalDateTime endDateConvert);

}
