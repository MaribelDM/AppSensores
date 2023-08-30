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
public class ActualizarNotificacionRequest {
	
	private Integer id;
	
	private Integer estado;
	
	private String observacion;

}
