package com.microservicio.app.services;

import java.util.List;

import com.microservicio.app.out.SensorComboOut;

public interface SensorService {

	public List<SensorComboOut> getCombo(String idUsuario, String funcion);
}
