package com.microservicio.app.out;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
	
	private String nombreSensor;
	
	private List<ValoresSensorOut> valoresSensor;

}
