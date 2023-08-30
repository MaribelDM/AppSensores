package com.microservicio.app.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.app.dtos.RegistroNuevoSensorRequest;
import com.microservicio.app.out.SensorComboOut;
import com.microservicio.app.services.SensorService;

@RestController
@RequestMapping("/v1/aplicacion/sensores")
@CrossOrigin(origins = "http://localhost:4200")
@EnableResourceServer
public class SensorController {
	
	@Autowired
	private SensorService service;
	
	@GetMapping
	public List<SensorComboOut> getComboSensores(String idUsuario, String funcion){
		return service.getCombo(idUsuario, funcion);
	}
	
	@PostMapping
	public void registroSensor(@RequestBody List<RegistroNuevoSensorRequest> request) {
		service.registroSensor(request);
	}
	
}
