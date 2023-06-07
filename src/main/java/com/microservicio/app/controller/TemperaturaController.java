package com.microservicio.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.app.out.TemperaturaEstadistica;
import com.microservicio.app.out.TemperaturaOut;
import com.microservicio.app.services.TemperaturaService;

@RestController
@RequestMapping("/v1/aplicacion/temperatura")
@CrossOrigin(origins = "http://localhost:4200")
public class TemperaturaController {
	
	@Autowired
	private TemperaturaService service;
	
	@GetMapping("/temperaturas")
	public List<TemperaturaOut> listar(){
		
		return service.findAll();
	}
	
	@GetMapping("/temperaturaActual")
	public TemperaturaOut actual(){
		
		return service.findLast();
	}

	@DeleteMapping("/eliminarHistorial")
	public String eliminarHumedades(){
		
		return service.eliminar();
	}
	
	@GetMapping("/media")
	public TemperaturaEstadistica media(){
		
		return service.media();
	}
	
	@GetMapping("/temperaturas-fecha")
	public List<TemperaturaOut> temperaturasPorFecha(@RequestParam String startDate, @RequestParam String endDate){
		
		return service.temperaturasPorFecha(startDate, endDate);
	}
	
}
