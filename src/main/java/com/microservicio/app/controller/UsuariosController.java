package com.microservicio.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.microservicio.app.entities.Usuario;
import com.microservicio.app.out.UsuarioOut;
import com.microservicio.app.repositories.UsuarioRepository;



@Controller
@RequestMapping("/v1/aplicacion")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuariosController {
	
	@Autowired
	private UsuarioRepository usuarioRepository ;
	
	@GetMapping("/usuario")
	@ResponseBody
	public UsuarioOut getUsuario() {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioRepository.findByName(name);
		return UsuarioOut.builder().id(usuario.getId()).name(usuario.getName()).role(usuario.getRole()).build();
	}
	
}
