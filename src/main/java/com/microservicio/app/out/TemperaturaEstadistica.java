package com.microservicio.app.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemperaturaEstadistica {
	
	private float media;
	
	private float valorMin;
	
	private float valorMax;
	
	private float valorActual;

}
