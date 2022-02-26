package com.microservicio.app.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.app.entities.Usuario;
import com.microservicio.app.out.InicioSesion;
import com.microservicio.app.repositories.UsuarioRepository;
import com.microservicio.app.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public String registrarUsuario(String nombre, String contraseña) {
		String registro = "KO";
		List<Usuario> users =  usuarioRepository.findByNombre(nombre);
		//if()buscar si ya existe 
		usuarioRepository.save(Usuario.builder().nombre(nombre).password(contraseña).build());
		
		
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getNombre().equals(nombre)) {
				registro = "OK";
			}
		}
		return registro;
	}

	@Override
	public InicioSesion iniciarSesion(String nombre, String contraseña) {
		boolean found = false, login = false;
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		
		for(int i = 0; i < usuarios.size(); i++) {
			
			if(usuarios.get(i).getNombre().equals(nombre)) {
				found = true;
				if(usuarios.get(i).getPassword().equals(contraseña)) {
					login = true;
				}
			}
		}
		return InicioSesion.builder().found(found).login(login).build();
	}	

}
