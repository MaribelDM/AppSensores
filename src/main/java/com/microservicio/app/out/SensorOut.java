package com.microservicio.app.out;

import java.util.List;

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
public class SensorOut {
	
	private String nombre;
	
	private List<ValoresSensorOut> valores;
	
	private TemperaturaEstadistica estadisticas;
	
	private String mensaje; 
	
}
