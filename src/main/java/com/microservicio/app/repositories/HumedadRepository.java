package com.microservicio.app.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.microservicio.app.entities.Humedad;

public interface HumedadRepository extends CrudRepository<Humedad, Long> {
	
	List<Humedad> findByNameOrderByFecha(String name);
	
	List<Humedad> findAllByOrderByFecha();
	
	List<Humedad> findByFechaBetween(LocalDateTime startDate, LocalDateTime endDate);

	void deleteById(Integer id);

}
