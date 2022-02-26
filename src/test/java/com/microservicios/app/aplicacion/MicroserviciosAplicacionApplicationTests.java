package com.microservicios.app.aplicacion;

import static org.junit.Assert.assertNotNull;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroserviciosAplicacionApplicationTests {

	
	@Autowired
	private ApplicationContext applicationContext ;
	
	@Test
	public void contextLoads() {
		assertNotNull(applicationContext);
	}
	
}
