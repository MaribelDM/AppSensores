package com.microservicio.app.services.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservicio.app.entities.Temperatura;
import com.microservicio.app.repositories.TemperaturaRepository;
import com.microservicio.app.services.TemperaturaService;

@Service
public class TemperaturaServiceImpl implements TemperaturaService {

	private TemperaturaRepository temperaturaRepository;
	
	@Override
	public List<Temperatura> getTemperaturas() {
		return (List<Temperatura>) temperaturaRepository.findAll();
	}

}
