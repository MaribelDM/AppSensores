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
import com.microservicio.app.out.HumedadOut;
import com.microservicio.app.repositories.HumedadRepository;
import com.microservicio.app.services.HumedadService;

@Service
public class HumedadServiceImpl implements HumedadService {

	Logger logger = LoggerFactory.getLogger(HumedadServiceImpl.class);
	
	@Autowired
	private HumedadRepository humedadRepository ;
	
	@Override
	public List<HumedadOut> findAll() {
		List <Humedad> humedades = (List<Humedad>) humedadRepository.findAllByOrderByFecha();
		List <HumedadOut> humedadesOut = mapearHumedades(humedades);
		
		return humedadesOut;
	}

	@Override
	public HumedadOut findLast() {
		List<Humedad> humedades = (List<Humedad>) humedadRepository.findAll();
		Humedad humedad = humedades.get(humedades.size()-1);

		return HumedadOut.builder().valor(humedad.getValor()).fecha(humedad.getFecha().toString()).build();
	}

	@Override
	public String eliminar() {
		String salida ="No se ha podido borrar el historial de humedades";
		humedadRepository.deleteAll();
		List<Humedad> humedades = (List<Humedad>) humedadRepository.findAll();
		if(humedades.isEmpty()) {
			salida ="ÉXITO al borrar el historial";
		}else if(humedades.size() == 1) {
			salida = "ÉXITO al borrar, actualizada nueva humedad";
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
	public List<HumedadOut> humedadesPorFecha(String startDate, String endDate) {
		List<HumedadOut> humedadesOut = new ArrayList<>();
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
	
	private List<HumedadOut> mapearHumedades(List<Humedad> humedades) {
		List<HumedadOut> humedadesOut = new ArrayList<>();
		for(int i = 0; i < humedades.size(); i++) {
			Humedad humedad = humedades.get(i);
			
			HumedadOut humedadOut = new HumedadOut() ;
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
