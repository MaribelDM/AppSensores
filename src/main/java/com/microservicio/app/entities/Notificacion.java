package com.microservicio.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="notificacion")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Integer estado;
	
	@Column(name = "id_usuario")
	private Integer idUsuario;
	
	@Column(name = "observacion_usuario")
	private String observacionUsuario;
	
	@Column(name = "observacion_admin")
	private String observacionAdmin;
	
	@Column(name ="tipo_sensor")
	private String tipoSensor;
	
	@Column(name ="nombre_sensor")
	private String nombreSensor;
	
	@Column(name ="hay_elementos_sensor")
	private String hayElementosSensor;
}
