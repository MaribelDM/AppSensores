package com.microservicio.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration

public class MicroserviciosAplicacionApplication {

	public static void main(String[] args) {
//		new SpringApplicationBuilder(MicroserviciosAplicacionApplication.class)
//				.web(WebApplicationType.SERVLET)
//				.run(args);
		 SpringApplication.run(MicroserviciosAplicacionApplication.class, args);
	}

}
