package com.microservicio.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroNuevoSensorRequest {
	
	private Integer idUsuario;
	
	private String idNotificacion;
	
	private String nombreSensor;

}
