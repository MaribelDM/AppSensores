package com.microservicio.app.services.implement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.app.entities.Temperatura;
import com.microservicio.app.out.TemperaturaEstadistica;
import com.microservicio.app.out.TemperaturaOut;
import com.microservicio.app.repositories.TemperaturaRepository;
import com.microservicio.app.services.TemperaturaService;

@Service
public class TemperaturaServiceImpl implements TemperaturaService {

	Logger logger = LoggerFactory.getLogger(TemperaturaServiceImpl.class);

	@Autowired
	private TemperaturaRepository temperaturaRepository;
	
	@Override
	public List<TemperaturaOut> findAll() {
		List<Temperatura> temperaturas = (List<Temperatura>) temperaturaRepository.findAll();
		List<TemperaturaOut> temperaturasOut = mapearTemperaturas(temperaturas);

		return temperaturasOut;
	}

	@Override
	public TemperaturaOut findLast() {
		List<Temperatura> temperaturas = (List<Temperatura>) temperaturaRepository.findAll();
		Temperatura temperatura = temperaturas.get(temperaturas.size() - 1);

		return TemperaturaOut.builder().valor(temperatura.getValor()).fecha(temperatura.getFecha().toString()).build();
	}

	@Override
	public String eliminar() {
		String salida = "No se ha podido borrar el historial de humedades";
		temperaturaRepository.deleteAll();
		List<Temperatura> temperaturas = (List<Temperatura>) temperaturaRepository.findAll();
		if (temperaturas.isEmpty()) {
			salida = "ÉXITO al borrar el historial";
		} else if (temperaturas.size() == 1) {
			salida = "ÉXITO al borrar, actualizada nueva humedad";
		}
		return salida;
	}

	@Override
	public TemperaturaEstadistica media() {
		float media = 0, max = 0, min = 0;
		List<Temperatura> temperaturas = (List<Temperatura>) temperaturaRepository.findAll();
		// String usuarioString ;
		max = temperaturas.get(0).getValor();
		min = temperaturas.get(0).getValor();
		for (int i = 0; i < temperaturas.size(); i++) {
			Temperatura temperatura = temperaturas.get(i);

			media += temperatura.getValor();

			if (max < temperatura.getValor()) {
				max = temperatura.getValor();
			} else if (min > temperatura.getValor()) {
				min = temperatura.getValor();
			}
		}
		media /= temperaturas.size();

		return TemperaturaEstadistica.builder().media(media).valorMax(max).valorMin(min).build();
	}

	@Override
	public List<TemperaturaOut> temperaturasPorFecha(String startDate, String endDate) {
		List<TemperaturaOut> temperaturasOut = new ArrayList<>();
		try {
			Timestamp startDateConvert = formatearFecha(startDate);
			Timestamp endDateConvert = formatearFecha(endDate);
			List<Temperatura> temperaturas = temperaturaRepository.findByFechaBetween(startDateConvert,
					endDateConvert);
			temperaturasOut = mapearTemperaturas(temperaturas);
		} catch (Exception e) {
			logger.error("Error al recoger humedades de base de datos", e);
			throw new InternalError(e);
		}
		return temperaturasOut;
	}
	
	private List<TemperaturaOut> mapearTemperaturas(List<Temperatura> temperaturas) {
		List<TemperaturaOut> temperaturasOut = new ArrayList<>();
		for (int i = 0; i < temperaturas.size(); i++) {
			Temperatura temperatura = temperaturas.get(i);

			TemperaturaOut temperaturaOut = new TemperaturaOut();
			if (temperatura != null) {
				temperaturaOut.setFecha(temperatura.getFecha().toString());
				temperaturaOut.setValor(temperatura.getValor());
			}
			temperaturasOut.add(temperaturaOut);
		}
		return temperaturasOut;
	}

	private static Timestamp formatearFecha(String fecha) {
		// Transformar fecha data por caracteres en un tipo timeStamp y así poder
		// realizar la busqueda en bbdd
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.parse(fecha, formatter);
		Timestamp localDatetime = Timestamp.valueOf(localDate);

		return localDatetime;
	}

}
