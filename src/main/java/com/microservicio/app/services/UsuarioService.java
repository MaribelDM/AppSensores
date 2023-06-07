package com.microservicio.app.services;

import com.microservicio.app.out.InicioSesion;

public interface UsuarioService {

	public void registrarUsuario(String nombre, String contraseña) throws Exception;
	
	public InicioSesion iniciarSesion(String nombre, String contraseña);
}
