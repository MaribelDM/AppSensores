package com.microservicio.app.services;

import java.util.List;

import com.microservicio.app.dtos.ActualizarNotificacionRequest;
import com.microservicio.app.dtos.PeticionNotificacionRequest;
import com.microservicio.app.out.NotificacionesResponse;
import com.microservicio.app.out.SensorNotificacion;


public interface NotificacionService {

	void introducirNotificacion(PeticionNotificacionRequest request);

	NotificacionesResponse obtenerNotificacionesPendientes();

	List<SensorNotificacion> obtenerMisNotificaciones(Integer[] estados);

	void actualizarNotificaciones(List<ActualizarNotificacionRequest> request, boolean flagAdmin);

	void eliminarNotificaciones(Integer[] ids);

}
