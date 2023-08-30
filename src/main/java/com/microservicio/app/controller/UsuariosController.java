package com.microservicio.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

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
		Usuario usuario = usuarioRepository.findByUsername(name);
		return UsuarioOut.builder().id(usuario.getId()).name(usuario.getUsername()).role(usuario.getRole()).build();
	}
	
	@GetMapping("/usuarios")
	@ResponseBody
	public List<UsuarioOut> getUsuarios() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario miUsuario = usuarioRepository.findByUsername(name);
		if (miUsuario.getRole() == "1") {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No dispone de los permisos necesarios");
		}
		List<Usuario> usuarios = usuarioRepository.findByRole("1");

		return usuarios.stream().map(usuario -> UsuarioOut.builder().id(usuario.getId()).name(usuario.getUsername())
				.role(usuario.getRole()).build()).collect(Collectors.toList());
	}

}
