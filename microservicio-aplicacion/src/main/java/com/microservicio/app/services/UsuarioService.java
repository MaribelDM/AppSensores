package com.microservicio.app.services;

import com.microservicio.app.out.InicioSesion;

public interface UsuarioService {

	public String registrarUsuario(String nombre, String contraseña);
	
	public InicioSesion iniciarSesion(String nombre, String contraseña);
}
