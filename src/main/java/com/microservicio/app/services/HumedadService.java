package com.microservicio.app.services;

import java.util.List;

import com.microservicio.app.out.HumedadEstadistica;
import com.microservicio.app.out.HumedadOut;

public interface HumedadService {
	
	public List<HumedadOut> findAll();

	public HumedadOut findLast();
	
	public String eliminar();
	
	public HumedadEstadistica media();

	public List<HumedadOut> encontrarHumedades(String startDate, String endDate);
}
