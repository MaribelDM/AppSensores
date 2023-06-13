package com.microservicio.app.services.implement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.app.entities.Sensor;
import com.microservicio.app.entities.Temperatura;
import com.microservicio.app.entities.Usuario;
import com.microservicio.app.out.SensorOut;
import com.microservicio.app.out.TemperaturaEstadistica;
import com.microservicio.app.out.TemperaturasOut;
import com.microservicio.app.out.ValoresSensorOut;
import com.microservicio.app.repositories.SensorRepository;
import com.microservicio.app.repositories.TemperaturaRepository;
import com.microservicio.app.repositories.UsuarioRepository;
import com.microservicio.app.services.TemperaturaService;

import javassist.tools.web.BadHttpRequest;

@Service
public class TemperaturaServiceImpl implements TemperaturaService {

	Logger logger = LoggerFactory.getLogger(TemperaturaServiceImpl.class);

	@Autowired
	private TemperaturaRepository temperaturaRepository;
	@Autowired
	private SensorRepository sensorRepository ;
	@Autowired
	private UsuarioRepository usuarioRepository ;
	
	@Override
	public TemperaturasOut findAllTemperaturasByUserAndIdSensor(String nameSensor, String startDate, String endDate)
			throws BadHttpRequest {
		List<Temperatura> temperaturas = new ArrayList<>();
		List<SensorOut> sensoresOut = new ArrayList<>();
		Usuario usuario = new Usuario();
		if (!ObjectUtils.isEmpty(nameSensor)) {
			Sensor sensor = sensorRepository.findByNombreAndTipo(nameSensor, "T");
			if (!ObjectUtils.isEmpty(startDate) && !ObjectUtils.isEmpty(endDate)) {
				LocalDateTime startDateConvert = formatearFecha(startDate);
				LocalDateTime endDateConvert = formatearFecha(endDate);
				temperaturas = temperaturaRepository.findByIdSensorAndFechaBetween(sensor.getId(), startDateConvert,
						endDateConvert);
			} else {
				temperaturas = temperaturaRepository.findByIdSensorOrderByFecha(sensor.getId());
			}
			List<ValoresSensorOut> valores = mapValoresSensoresOut(temperaturas);
			sensoresOut
					.add(SensorOut.builder().valores(valores).nombre(nameSensor)
							.estadisticas(ObjectUtils.isEmpty(valores) ? null : media(valores)).build());
			usuario = usuarioRepository.findById(sensor.getIdUsuario()).orElse(null);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Especificar nombre del sensor");
		}

		return TemperaturasOut.builder().sensor(sensoresOut).usuario(usuario.getUsername()).build();
	}

	private List<SensorOut> getSensoresUsuario(String idUsuario) {
		List<Sensor> sensoresAsociados = sensorRepository.findByIdUsuarioAndTipo(Integer.valueOf(idUsuario), "T");
		List<SensorOut> sensoresOut = new ArrayList<>();
		
		for (Sensor sensor : sensoresAsociados) {
			List<ValoresSensorOut> valores = mapValoresSensoresOut(
					temperaturaRepository.findByIdSensorOrderByFecha(sensor.getId()));
			sensoresOut.add(SensorOut.builder().valores(valores).nombre(sensor.getNombre()).estadisticas(media(valores))
					.build());
		}
		return sensoresOut;
	}

	private List<ValoresSensorOut> mapValoresSensoresOut(List<Temperatura> temperaturas) {
		List<ValoresSensorOut> valores = new ArrayList<>();
		temperaturas.forEach(humedad -> valores.add(
				ValoresSensorOut.builder().valor(humedad.getValor()).fecha(humedad.getFecha().toString()).build()));
		return valores;
		
	}
	
//	@Override
//	public List<TemperaturasOut> findAll() {
//		List<Temperatura> temperaturas = (List<Temperatura>) temperaturaRepository.findAll();
//		List<TemperaturasOut> temperaturasOut = mapearTemperaturas(temperaturas);
//
//		return temperaturasOut;
//	}

//	@Override
//	public TemperaturasOut findLast() {
//		List<Temperatura> temperaturas = (List<Temperatura>) temperaturaRepository.findAll();
//		Temperatura temperatura = temperaturas.get(temperaturas.size() - 1);
//
//		return TemperaturasOut.builder().valor(temperatura.getValor()).fecha(temperatura.getFecha().toString()).build();
//	}

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

	public TemperaturaEstadistica media(List<ValoresSensorOut> valoresTemperaturas) {
		float media = 0, max = 0, min = 0;
//		List<Temperatura> temperaturas = (List<Temperatura>) temperaturaRepository.findAll();
		// String usuarioString ;
		max = valoresTemperaturas.get(0).getValor();
		min = valoresTemperaturas.get(0).getValor();
		for (int i = 0; i < valoresTemperaturas.size(); i++) {
			ValoresSensorOut temperatura = valoresTemperaturas.get(i);

			media += temperatura.getValor();

			if (max < temperatura.getValor()) {
				max = temperatura.getValor();
			} else if (min > temperatura.getValor()) {
				min = temperatura.getValor();
			}
		}
		media /= valoresTemperaturas.size();

		return TemperaturaEstadistica.builder().media(media).valorMax(max).valorMin(min).build();
	}

//	@Override
//	public List<TemperaturasOut> temperaturasPorFecha(String startDate, String endDate) {
//		List<TemperaturasOut> temperaturasOut = new ArrayList<>();
//		try {
//			Timestamp startDateConvert = formatearFecha(startDate);
//			Timestamp endDateConvert = formatearFecha(endDate);
//			List<Temperatura> temperaturas = temperaturaRepository.findByFechaBetween(startDateConvert,
//					endDateConvert);
//			temperaturasOut = mapearTemperaturas(temperaturas);
//		} catch (Exception e) {
//			logger.error("Error al recoger humedades de base de datos", e);
//			throw new InternalError(e);
//		}
//		return temperaturasOut;
//	}
//	
//	private List<TemperaturasOut> mapearTemperaturas(List<Temperatura> temperaturas) {
//		List<TemperaturasOut> temperaturasOut = new ArrayList<>();
//		for (int i = 0; i < temperaturas.size(); i++) {
//			Temperatura temperatura = temperaturas.get(i);
//
//			TemperaturasOut temperaturaOut = new TemperaturasOut();
//			if (temperatura != null) {
//				temperaturaOut.setFecha(temperatura.getFecha().toString());
//				temperaturaOut.setValor(temperatura.getValor());
//			}
//			temperaturasOut.add(temperaturaOut);
//		}
//		return temperaturasOut;
//	}

	private static LocalDateTime formatearFecha(String fecha) {
		// Transformar fecha data por caracteres en un tipo timeStamp y así poder
		// realizar la busqueda en bbdd
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.parse(fecha, formatter);
		//Timestamp localDatetime = Timestamp.valueOf(localDate);

		return localDate;
	}

	@Override
	public List<TemperaturasOut> findAll() {
		List<TemperaturasOut> response = new ArrayList<>();
		List<Usuario> usuarios = usuarioRepository.findAll();
		usuarios.forEach(usuario -> {
			if (usuario.getRole().equals("1")) {
				response.add(TemperaturasOut.builder().sensor(getSensoresUsuario(usuario.getId().toString()))
						.usuario(usuario.getUsername()).build());
			}
		});
		return response;
	}

}
