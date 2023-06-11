package com.microservicio.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="sensor")
@Setter
@Getter
public class Sensor {

	@Id
	private Integer id;
	
	@Column
	private String nombre;
	
	@Column
	private String tipo;
	
	@Column(name = "id_usuario")
	private Integer idUsuario;
	
}
