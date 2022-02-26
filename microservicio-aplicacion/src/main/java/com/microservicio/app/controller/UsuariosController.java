package com.microservicio.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservicio.app.out.InicioSesion;
import com.microservicio.app.services.UsuarioService;



@Controller
@RequestMapping("/v1/aplicacion/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuariosController {
	
	 @Autowired
	private UsuarioService usuarioService;

	
//	 public void setJdbcUserDAO(UsuarioService usuarioService) {
//		 this.usuarioService= usuarioService;
//	 }
	 
	@PutMapping("/registro/{contraseña}/{nombre}")
	public String registrarUsuario(String nombre, String contraseña) {
		return usuarioService.registrarUsuario(nombre, contraseña);
	}
	
	@GetMapping("/sesion")
	public InicioSesion iniciarSesion(String nombre, String contraseña) {
		InicioSesion sesion = usuarioService.iniciarSesion(nombre, contraseña);
		
		return sesion;
	} 
}
