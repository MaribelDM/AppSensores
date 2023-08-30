package com.microservicio.app.services.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import com.microservicio.app.dtos.ActualizarNotificacionRequest;
import com.microservicio.app.dtos.PeticionNotificacionRequest;
import com.microservicio.app.entities.EstadoNotificacionEnum;
import com.microservicio.app.entities.Notificacion;
import com.microservicio.app.entities.Sensor;
import com.microservicio.app.entities.Usuario;
import com.microservicio.app.out.NotificacionesResponse;
import com.microservicio.app.out.SensorNotificacion;
import com.microservicio.app.repositories.NotificacionRepository;
import com.microservicio.app.repositories.SensorRepository;
import com.microservicio.app.repositories.UsuarioRepository;
import com.microservicio.app.services.NotificacionService;

@Service
public class NotificacionServiceImpl implements NotificacionService {
	
	@Autowired
	private NotificacionRepository notificacionRepository;
	
	@Autowired
	private SensorRepository sensorRepository;
	
	@Autowired UsuarioRepository usuarioRepository;

	@Override
	public void introducirNotificacion(PeticionNotificacionRequest request) {
		Sensor sensor = sensorRepository.findByNombreAndTipoAndIdUsuario(request.getNombreSensor(),
				request.getTipoSensor(), request.getIdUsuario());
		if (ObjectUtils.isEmpty(sensor)) {
			notificacionRepository.save(Notificacion.builder().estado(EstadoNotificacionEnum.PENDIENTE_NO_LEIDA.getId())
					.idUsuario(request.getIdUsuario()).observacionUsuario(request.getObservaciones())
					.tipoSensor(request.getTipoSensor()).nombreSensor(request.getNombreSensor())
					.hayElementosSensor(request.getHayElementosSensor()).build());
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Los datos no son válidos. Compruebe que este sensor no existe previamente para la funcionalidad deseada.");
		}
	}

	@Override
	public NotificacionesResponse obtenerNotificacionesPendientes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SensorNotificacion> obtenerMisNotificaciones(Integer[] estados) {
		Usuario usuario = usuarioRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Notificacion> notificaciones = new ArrayList<>();
		if(usuario.getRole().equals("0") ) {
			notificaciones = notificacionRepository.findByEstadoIn(estados);
		}else {
			notificaciones = notificacionRepository.findByEstadoInAndIdUsuario(estados, usuario.getId());
		} 
		return notificaciones.stream()
				.map(notificacion -> SensorNotificacion.builder().id(notificacion.getId().toString())
						.nombre(notificacion.getNombreSensor()).tipo(notificacion.getTipoSensor())
						.hayElementos(notificacion.getHayElementosSensor())
						.observacionUsuario(notificacion.getObservacionUsuario())
						.observacionAdmin(notificacion.getObservacionAdmin())
						.estado(EstadoNotificacionEnum.of(notificacion.getEstado()).getName())
						.nombreUsuario(usuarioRepository.getById(notificacion.getIdUsuario()).getUsername()).build())
				.collect(Collectors.toList());
	}

	@Override
	public void actualizarNotificaciones(List<ActualizarNotificacionRequest> request) {
		boolean admin = usuarioRepository
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getRole() == "ADMIN";
		request.forEach(notificacion -> {
			Notificacion notificacionEntity = notificacionRepository.findById(notificacion.getId()).orElse(null);
			notificacionEntity.setEstado(notificacion.getEstado());
			if (admin) {
				notificacionEntity.setObservacionAdmin(notificacion.getObservacion());
			} else {
				notificacionEntity.setObservacionUsuario(notificacion.getObservacion());
			}
			notificacionRepository.save(notificacionEntity);
		});
	}

	@Override
	public void eliminarNotificaciones(Integer[] ids) {
		for(int i = 0; i< ids.length ; i++) {
			notificacionRepository.deleteById(ids[i]);
		}
//		notificacionRepository.deleteByIdIn(ids);
	}

}
