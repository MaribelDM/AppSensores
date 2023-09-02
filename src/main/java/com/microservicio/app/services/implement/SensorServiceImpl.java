package com.microservicio.app.services.implement;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.app.dtos.RegistroNuevoSensorRequest;
import com.microservicio.app.entities.Notificacion;
import com.microservicio.app.entities.Sensor;
import com.microservicio.app.out.SensorComboOut;
import com.microservicio.app.repositories.NotificacionRepository;
import com.microservicio.app.repositories.SensorRepository;
import com.microservicio.app.services.SensorService;

@Service
public class SensorServiceImpl implements SensorService {

	@Autowired
	private SensorRepository sensorRepository;
	
	@Autowired
	private NotificacionRepository notificacionRepository;
	
	@Override
	public List<SensorComboOut> getCombo(String idUsuario, String funcion) {
		return mapCombo(sensorRepository.findByIdUsuarioAndTipo(Integer.valueOf(idUsuario), funcion));
	}

	private List<SensorComboOut> mapCombo(List<Sensor> sensores) {
		return sensores.stream()
				.map(sensor -> SensorComboOut.builder().nombre(sensor.getNombre()).id(sensor.getId().toString()).build())
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void registroSensor(List<RegistroNuevoSensorRequest> request) {
		request.forEach(nuevoSensor -> {
			registroSensor(nuevoSensor);
			
		});
	}

	private void registroSensor(RegistroNuevoSensorRequest nuevoSensor) {
		// eliminar peticiones sensor 
		Notificacion notificacion = notificacionRepository.findById(Integer.valueOf(nuevoSensor.getIdNotificacion())).orElse(null);
		notificacionRepository.deleteById(notificacion.getId());
		// almacenar sensor nuevo
		Sensor sensor = sensorRepository.findByNombreAndTipoAndIdUsuario(nuevoSensor.getNombreSensor(), notificacion.getTipoSensor(), nuevoSensor.getIdUsuario());
		if(ObjectUtils.isEmpty(sensor) && sensor == null) {
		sensorRepository.save(Sensor.builder().idUsuario(nuevoSensor.getIdUsuario())
				.nombre(nuevoSensor.getNombreSensor()).tipo(notificacion.getTipoSensor()).build());
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"El sensor con nombre "+ notificacion.getNombreSensor() +" ya existe");
		}

	}
	
}
