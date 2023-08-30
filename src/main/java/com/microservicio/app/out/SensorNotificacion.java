package com.microservicio.app.out;

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
public class SensorNotificacion {
	
	private String id; 
	
	private String nombre; 
	
	private String tipo;
	
	private String hayElementos;
	
	private String estado;
	
	private String observacionUsuario;
	
	private String observacionAdmin;
	
	private String nombreUsuario;

}
