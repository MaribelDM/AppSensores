package com.microservicio.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.app.out.HumedadEstadistica;
import com.microservicio.app.out.HumedadOut;
import com.microservicio.app.services.HumedadService;

@RestController
@RequestMapping("/v1/aplicacion/humedad")
@CrossOrigin(origins = "http://localhost:4200")
public class HumedadController {
	
	@Autowired
	private HumedadService service;
	
	@GetMapping("/humedades")
	public List<HumedadOut> listar(){
		
		return service.findAll();
	}
	
	@GetMapping("/humedadActual")
	public HumedadOut actual(){
		
		return service.findLast();
	}

	@DeleteMapping("/eliminarHistorial")
	public String eliminarHumedades(){
		
		return service.eliminar();
	}
	
	@GetMapping("/media")
	public HumedadEstadistica media(){
		
		return service.media();
	}
	
}
