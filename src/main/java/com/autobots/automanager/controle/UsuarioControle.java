package com.autobots.automanager.controle;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.DTO.UsuarioDTO;
import com.autobots.automanager.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/")
	public ResponseEntity<List<UsuarioDTO>> obterUsuarios(){
		try {
			List<UsuarioDTO> usuarios = service.obterClientes();
			if(usuarios != null) {
				return ResponseEntity.status(HttpStatus.FOUND).body(usuarios);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}	catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} 	catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> obterUsuario(@PathVariable Long id){
		try {
			UsuarioDTO usuario = service.obterCliente(id);
			if(usuario != null) {
				return ResponseEntity.status(HttpStatus.FOUND).body(usuario);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}	catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} 	catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuario){
		try {
			UsuarioDTO usuarioCadastrado = service.cadastrarCliente(usuario);
			if(usuarioCadastrado != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	} 	catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<UsuarioDTO> atualziarUsuario(@RequestBody UsuarioDTO usuario){
		try {
			UsuarioDTO usuarioAtualizado = service.atualizarCliente(usuario);
			if(usuarioAtualizado != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	} 	catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/deletar")
	public ResponseEntity<Void> deletarUsuario(@RequestBody UsuarioDTO usuario){
		try {
			service.deletarCliente(usuario);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	} 	catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}