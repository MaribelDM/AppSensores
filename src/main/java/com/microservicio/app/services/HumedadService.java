package com.microservicio.app.services;

import java.util.List;

import com.microservicio.app.out.HumedadEstadistica;
import com.microservicio.app.out.HumedadesOut;

public interface HumedadService {
	
	public List<HumedadesOut> findAll(String name);

	public HumedadesOut findLast();
	
	public String eliminar(String date);
	
	public HumedadEstadistica media();

	public List<HumedadesOut> humedadesPorFecha(String startDate, String endDate);
}
