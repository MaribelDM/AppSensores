package com.microservicio.app.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HumedadEstadistica {
	private float media;
	
	private float valorMin;
	
	private float valorMax;
	
	private String usuario;
	
}
