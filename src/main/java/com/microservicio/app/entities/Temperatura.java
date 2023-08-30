package com.microservicio.app.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "temperatura") 
public class Temperatura {

	@Id
	private Integer id;
	
	@Column
	private float valor; 
	
	@Column
	private LocalDateTime fecha;
	
	@Column(name = "id_sensor")
	private Integer idSensor;
}
