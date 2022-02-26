package com.microservicio.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/aplicacion/temperatura")
public class TemperaturaController {
	
	@GetMapping
	public float getTemperatura() {
		return 0;
	}
	
}
