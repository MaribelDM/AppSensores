package com.microservicio.app.services;

import java.util.List;

import com.microservicio.app.out.TemperaturasOut;

import javassist.tools.web.BadHttpRequest;

public interface TemperaturaService {

//	public List<TemperaturasOut> findAll();

//	public TemperaturasOut findLast();
	
	public String eliminar();

	public List<TemperaturasOut> findAll();

	TemperaturasOut findAllTemperaturasByUserAndIdSensor(String idSensor, String startDate, String endDate)
			throws BadHttpRequest;
	
//	public TemperaturaEstadistica media();

//	public List<TemperaturasOut> temperaturasPorFecha(String startDate, String endDate);
}
