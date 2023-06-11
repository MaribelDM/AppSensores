package com.microservicio.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.app.out.HumedadesOut;
import com.microservicio.app.services.HumedadService;

@RestController
@RequestMapping("/v1/aplicacion/humedad")
@CrossOrigin(origins = "http://localhost:4200")
@EnableResourceServer
public class HumedadController {
	
	@Autowired
	private HumedadService service;
	
	@GetMapping("/humedades-filtrado")
	public HumedadesOut listar(String idUsuario, String nameSensor){
		return service.findAllHumedadesByUserAndIdSensor(idUsuario, nameSensor);
	}
	
	@GetMapping("/humedades")
	public List<HumedadesOut> listarAll(){
		return service.findAll();
	}
	
//	@GetMapping("/humedadActual")
//	public HumedadesOut actual(){
//		
//		return service.findLast();
//	}

	@DeleteMapping("/eliminarDia/{date}")
	public String eliminarHumedades(String date){
		
		return service.eliminar(date);
	}
	
//	@GetMapping("/media")
//	public HumedadEstadistica media(){
//		
//		return service.media();
//	}
//	
//	@GetMapping("/humedades/{startDate}/{endDate}")
//	public List<HumedadesOut> humedadesPorFecha(String startDate, String endDate){
//		
//		return service.humedadesPorFecha(startDate, endDate);
//	}
	
}
