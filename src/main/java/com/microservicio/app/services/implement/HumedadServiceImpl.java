package com.microservicio.app.services.implement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.microservicio.app.entities.Humedad;
import com.microservicio.app.entities.Sensor;
import com.microservicio.app.entities.Usuario;
import com.microservicio.app.out.HumedadesOut;
import com.microservicio.app.out.SensorOut;
import com.microservicio.app.out.TemperaturaEstadistica;
import com.microservicio.app.out.ValoresSensorOut;
import com.microservicio.app.repositories.HumedadRepository;
import com.microservicio.app.repositories.SensorRepository;
import com.microservicio.app.repositories.UsuarioRepository;
import com.microservicio.app.services.HumedadService;

@Service
public class HumedadServiceImpl implements HumedadService {

	Logger logger = LoggerFactory.getLogger(HumedadServiceImpl.class);
	
	@Autowired
	private HumedadRepository humedadRepository ;
	@Autowired
	private SensorRepository sensorRepository ;
	@Autowired
	private UsuarioRepository usuarioRepository ;
	
	@Override
	public HumedadesOut findAllHumedadesByUserAndIdSensor(String idUsuario, String nameSensor) {
		List<Humedad> humedades = new ArrayList<>();

		List<SensorOut> sensoresOut = new ArrayList<>();
		Usuario usuario = new Usuario();
		if (!ObjectUtils.isEmpty(nameSensor) && ObjectUtils.isEmpty(idUsuario)) {
			Sensor sensor = sensorRepository.findByNombreAndTipo(nameSensor, "H");
			humedades = humedadRepository.findByIdSensorOrderByFecha(sensor.getId());
			List<ValoresSensorOut> valores = mapValoresSensoresOut(humedades);
			sensoresOut.add(SensorOut.builder().valores(valores).nombre(nameSensor)
					.estadisticas(ObjectUtils.isEmpty(valores) ? null : media(valores))
					.mensaje(
							valores.size() == 0
									? "No hay valores para este sensor con identificador " + sensor.getId()
											+ ". Compruebe que tiene sensor fisico asociado."
									: null)
					.build());
			usuario = usuarioRepository.findById(sensor.getIdUsuario()).orElse(null);
		} else if (!ObjectUtils.isEmpty(idUsuario)) {
			sensoresOut = getSensoresUsuario(idUsuario);
			usuario = usuarioRepository.findById(Integer.valueOf(idUsuario)).orElse(null);
		}
		return HumedadesOut.builder().sensor(sensoresOut).usuario(usuario.getUsername()).build();

	}

	private List<SensorOut> getSensoresUsuario(String idUsuario) {
		List<Sensor> sensoresAsociados = sensorRepository.findByIdUsuarioAndTipo(Integer.valueOf(idUsuario), "H");
		List<SensorOut> sensoresOut = new ArrayList<>();
		
		for (Sensor sensor : sensoresAsociados) {
			List<ValoresSensorOut> valores = mapValoresSensoresOut(
					humedadRepository.findByIdSensorOrderByFecha(sensor.getId()));
			sensoresOut.add(SensorOut.builder().valores(valores).nombre(sensor.getNombre())
					.estadisticas(ObjectUtils.isEmpty(valores) ? null : media(valores))
					.mensaje(
							valores.size() == 0
									? "No hay valores para este sensor con identificador " + sensor.getId()
											+ ". Compruebe que tiene sensor fisico asociado."
									: null)
					.build());
		}
		return sensoresOut;
	}

	private List<ValoresSensorOut> mapValoresSensoresOut(List<Humedad> humedades) {
		List<ValoresSensorOut> valores = new ArrayList<>();
		humedades.forEach(humedad -> valores.add(
				ValoresSensorOut.builder().valor(humedad.getValor()).fecha(humedad.getFecha().toString()).build()));
		return valores;
		
	}

//	@Override
//	public HumedadesOut findLast() {
//		List<Humedad> humedades = (List<Humedad>) humedadRepository.findAll();
//		Humedad humedad = humedades.get(humedades.size()-1);
//		
//		
//
//		return HumedadesOut.builder().valor(humedad.getValor()).fecha(humedad.getFecha().toString()).build();
//	}

	@Override
	public String eliminar(String date) {
		String salida ="No se ha podido borrar el historial de humedades";
		Integer tamañoInicial = ((List<Humedad>) humedadRepository.findAll()).size();
		LocalDateTime startDateConvert =  formatearFecha("2022-05-07 14:15:00");
		LocalDateTime endDateConvert = formatearFecha("2022-05-07 14:16:00");
		List<Humedad> humedadesABorrar = humedadRepository.findByFechaBetween(startDateConvert, endDateConvert);
		for(Humedad humedad: humedadesABorrar ) {
			humedadRepository.deleteById(humedad.getId());
		}
		Integer tamañoFinal = ((List<Humedad>) humedadRepository.findAll()).size();
		if(tamañoInicial > tamañoFinal) {
			salida ="ÉXITO al borrar el historial";
		}
		return salida;
	}

	public TemperaturaEstadistica media(List<ValoresSensorOut> valores) {
		float media = 0, max = 0, min = 0, actual = 0;
//		List <Humedad> humedades = (List<Humedad>) humedadRepository.findAll();
		// String usuarioString ;
		actual = valores.get(valores.size() -1).getValor();
		max = valores.get(0).getValor();
		min = valores.get(0).getValor();
		for (int i = 0; i < valores.size(); i++) {
			ValoresSensorOut humedad = valores.get(i);

			media += humedad.getValor();

			if (max < humedad.getValor()) {
				max = humedad.getValor();
			} else if (min > humedad.getValor()) {
				min = humedad.getValor();
			}
		}
		media /= valores.size();

		return TemperaturaEstadistica.builder().media(media).valorMax(max).valorMin(min).valorActual(actual).build();
	}

//	@Override
//	public List<HumedadesOut> humedadesPorFecha(String startDate, String endDate) {
//		List<HumedadesOut> humedadesOut = new ArrayList<>();
//		try {
//		LocalDateTime startDateConvert =  formatearFecha(startDate);
//		LocalDateTime endDateConvert = formatearFecha(endDate);
//		List<Humedad> humedades = humedadRepository.findByFechaBetween(startDateConvert, endDateConvert);
//		humedadesOut = mapearHumedades(humedades);
//		}catch (Exception e) {
//			logger.error("Error al recoger humedades de base de datos", e);
//			throw new InternalError();
//		}
//		return humedadesOut;
//	}
	
//	private List<HumedadesOut> mapearHumedades(List<Humedad> humedades) {
//		List<HumedadesOut> humedadesOut = new ArrayList<>();
//		for(int i = 0; i < humedades.size(); i++) {
//			Humedad humedad = humedades.get(i);
//			
//			HumedadesOut humedadOut = new HumedadesOut() ;
//			if(humedad != null) {
//				humedadOut.setFecha(humedad.getFecha().toString());
//				humedadOut.setValor(humedad.getValor());
//			}
//			humedadesOut.add(humedadOut);
//		}
//		return humedadesOut;
//	}

	private static LocalDateTime formatearFecha(String fecha)
    {

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    	LocalDateTime localDate = LocalDateTime.parse(fecha, formatter);
    	return localDate;
        
		
    }

	@Override
	public List<HumedadesOut> findAll() {
		List<HumedadesOut> response = new ArrayList<>();
		List<Usuario> usuarios = usuarioRepository.findAll();
		usuarios.forEach(usuario -> {
			if (usuario.getRole().equals("0")) {
				response.add(HumedadesOut.builder().sensor(getSensoresUsuario(usuario.getId().toString()))
						.usuario(usuario.getUsername()).build());
			}
		});
		return response;
	}
	
}
