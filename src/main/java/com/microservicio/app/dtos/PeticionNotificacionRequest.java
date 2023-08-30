package com.microservicio.app.dtos;

import com.sun.istack.NotNull;

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
public class PeticionNotificacionRequest {
	
	@NotNull
	private Integer idUsuario;
	
	@NotNull
	private String observaciones;
	
	@NotNull
	private String tipoSensor;
	
	@NotNull
	private String nombreSensor;
	
	@NotNull
	private String hayElementosSensor;

}
