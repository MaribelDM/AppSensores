package com.microservicio.app.services.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.app.entities.Sensor;
import com.microservicio.app.out.SensorComboOut;
import com.microservicio.app.repositories.SensorRepository;
import com.microservicio.app.services.SensorService;

@Service
public class SensorServiceImpl implements SensorService {

	Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);
	
	@Autowired
	private SensorRepository sensorRepository ;
	
	
	@Override
	public List<SensorComboOut> getCombo(String idUsuario, String funcion) {
		return mapCombo(sensorRepository.findByIdUsuarioAndTipo(Integer.valueOf(idUsuario), funcion));
	}


	private List<SensorComboOut> mapCombo(List<Sensor> sensores) {
		return sensores.stream().map(sensor -> SensorComboOut.builder().nombre(sensor.getNombre()).build())
				.collect(Collectors.toList());
	}
	
}
