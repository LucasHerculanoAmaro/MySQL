package com.generation.minhaLojaDeGames.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.minhaLojaDeGames.model.Usuario;
import com.generation.minhaLojaDeGames.model.UsuarioLogin;
import com.generation.minhaLojaDeGames.repository.UsuarioRepository;
import com.generation.minhaLojaDeGames.service.UsuarioService;

@RestController
@RequestMapping ( "/usuarios" )
@CrossOrigin ( origins = " * " , allowedHeaders = " * " )
public  class  UsuarioController {

	@Autowired
	private  UsuarioService usuarioService;

	@Autowired
	private  UsuarioRepository usuarioRepository;
	
	//Este método retorna todos os usuários cadastrados no Banco de Dados
	@GetMapping ( "/all" )
		public ResponseEntity < List< Usuario > > getAll (){
		return ResponseEntity . ok(usuarioRepository . findAll());
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> login(@RequestBody Optional<UsuarioLogin> user) {
		return usuarioService.autenticarUsuario(user)
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> postUsuario(@Valid @RequestBody Usuario usuario) {
		return usuarioService.cadastrarUsuario(usuario)
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());	
	}
}
