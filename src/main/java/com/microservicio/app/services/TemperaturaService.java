package com.microservicio.app.services;

import java.util.List;

import com.microservicio.app.out.TemperaturaEstadistica;
import com.microservicio.app.out.TemperaturaOut;

public interface TemperaturaService {

	public List<TemperaturaOut> findAll();

	public TemperaturaOut findLast();
	
	public String eliminar();
	
	public TemperaturaEstadistica media();

	public List<TemperaturaOut> temperaturasPorFecha(String startDate, String endDate);
}
