package com.microservicio.app.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.app.entities.Humedad;

@Repository
public interface HumedadRepository extends CrudRepository<Humedad, Integer> {
	
//	List<Humedad> findByNameOrderByFecha(String name);
	
	List<Humedad> findAllByOrderByFecha();
	
	List<Humedad> findByFechaBetween(LocalDateTime startDate, LocalDateTime endDate);

	void deleteById(Integer id);
	
	List<Humedad> findByIdSensorOrderByFecha(Integer idSensor);

}
