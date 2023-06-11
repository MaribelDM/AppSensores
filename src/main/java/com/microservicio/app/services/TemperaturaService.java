package com.microservicio.app.services;

import java.util.List;

import com.microservicio.app.out.TemperaturasOut;

public interface TemperaturaService {

//	public List<TemperaturasOut> findAll();

//	public TemperaturasOut findLast();
	
	public String eliminar();

	TemperaturasOut findAllTemperaturasByUserAndIdSensor(String idUsuario, String nameSensor);

	public List<TemperaturasOut> findAll();
	
//	public TemperaturaEstadistica media();

//	public List<TemperaturasOut> temperaturasPorFecha(String startDate, String endDate);
}
