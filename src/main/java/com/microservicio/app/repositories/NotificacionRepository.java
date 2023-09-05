package com.microservicio.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.app.entities.Notificacion;

@Repository
public interface NotificacionRepository extends CrudRepository<Notificacion, Integer> {

	List<Notificacion> findByEstadoInAndIdUsuario(Integer[] estado, Integer idUsuario);
	
	List<Notificacion> findByEstadoIn(Integer[] estado);
	
	void deleteByIdIn(Integer[] id);
	
	void deleteByNombreSensorAndTipoSensor(String nombreSensor, String tipoSensor);

}
