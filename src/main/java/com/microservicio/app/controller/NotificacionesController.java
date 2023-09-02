package com.microservicio.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.app.dtos.ActualizarNotificacionRequest;
import com.microservicio.app.dtos.PeticionNotificacionRequest;
import com.microservicio.app.out.NotificacionesResponse;
import com.microservicio.app.out.SensorNotificacion;
import com.microservicio.app.services.NotificacionService;



@RestController
@RequestMapping("/v1/aplicacion/notificacion")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificacionesController {
	
	@Autowired
	private NotificacionService notificacionService;
	
	@PostMapping
	public void introducirNotificacion(@RequestBody PeticionNotificacionRequest request) {
		notificacionService.introducirNotificacion(request);
	}
	
	@GetMapping("/all")
	public NotificacionesResponse obtenerNotificacionesPendientes() {
		return notificacionService.obtenerNotificacionesPendientes();
	}
	
	@GetMapping
	@ResponseBody
	public List<SensorNotificacion> obtenerMisNotificaciones(Integer[] estados) {
		return notificacionService.obtenerMisNotificaciones(estados);
	}
	
	@PutMapping
	public void actualizarNotificaciones(@RequestBody List<ActualizarNotificacionRequest> request, boolean flagAdmin) {
		notificacionService.actualizarNotificaciones(request, flagAdmin);
	}
	
	@DeleteMapping
	public void actualizarNotificaciones(Integer[] ids) {
		notificacionService.eliminarNotificaciones(ids);
	}
	
}
