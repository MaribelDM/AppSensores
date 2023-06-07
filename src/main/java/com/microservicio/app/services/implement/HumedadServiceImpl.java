package com.microservicio.app.services.implement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.app.entities.Humedad;
import com.microservicio.app.out.HumedadEstadistica;
import com.microservicio.app.out.HumedadesOut;
import com.microservicio.app.repositories.HumedadRepository;
import com.microservicio.app.services.HumedadService;

@Service
public class HumedadServiceImpl implements HumedadService {

	Logger logger = LoggerFactory.getLogger(HumedadServiceImpl.class);
	
	@Autowired
	private HumedadRepository humedadRepository ;
	
	@Override
	public List<HumedadesOut> findAll(String name) {
		List <Humedad> humedades = new ArrayList<>();
		if(name != null) {
		 humedades = humedadRepository.findByNameOrderByFecha(name);
		}else {
			humedades = humedadRepository.findAllByOrderByFecha();
		}
		List <HumedadesOut> humedadesOut = mapearHumedades(humedades);
		
		return humedadesOut;
	}

	@Override
	public HumedadesOut findLast() {
		List<Humedad> humedades = (List<Humedad>) humedadRepository.findAll();
		Humedad humedad = humedades.get(humedades.size()-1);
		
		

		return HumedadesOut.builder().valor(humedad.getValor()).fecha(humedad.getFecha().toString()).build();
	}

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

	@Override
	public HumedadEstadistica media() {
		float media = 0 , max = 0, min = 0;
		List <Humedad> humedades = (List<Humedad>) humedadRepository.findAll();
		//String usuarioString ;
		max = humedades.get(0).getValor();
		min = humedades.get(0).getValor();
		for(int i = 0; i < humedades.size(); i++) {
			Humedad humedad = humedades.get(i);
			
			media += humedad.getValor();
			
			if(max < humedad.getValor()) {
				max = humedad.getValor();
			}else if(min > humedad.getValor()) {
				min = humedad.getValor();
			}
		}
		media /= humedades.size();
		
		return HumedadEstadistica.builder().media(media).valorMax(max).valorMin(min).build();
	}

	@Override
	public List<HumedadesOut> humedadesPorFecha(String startDate, String endDate) {
		List<HumedadesOut> humedadesOut = new ArrayList<>();
		try {
		LocalDateTime startDateConvert =  formatearFecha(startDate);
		LocalDateTime endDateConvert = formatearFecha(endDate);
		List<Humedad> humedades = humedadRepository.findByFechaBetween(startDateConvert, endDateConvert);
		humedadesOut = mapearHumedades(humedades);
		}catch (Exception e) {
			logger.error("Error al recoger humedades de base de datos", e);
			throw new InternalError();
		}
		return humedadesOut;
	}
	
	private List<HumedadesOut> mapearHumedades(List<Humedad> humedades) {
		List<HumedadesOut> humedadesOut = new ArrayList<>();
		for(int i = 0; i < humedades.size(); i++) {
			Humedad humedad = humedades.get(i);
			
			HumedadesOut humedadOut = new HumedadesOut() ;
			if(humedad != null) {
				humedadOut.setFecha(humedad.getFecha().toString());
				humedadOut.setValor(humedad.getValor());
			}
			humedadesOut.add(humedadOut);
		}
		return humedadesOut;
	}

	private static LocalDateTime formatearFecha(String fecha)
    {

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    	LocalDateTime localDate = LocalDateTime.parse(fecha, formatter);
    	return localDate;
        
		
    }
	
}
