package com.autobots.sistema.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.repositorios.RepositorioUsuario;

@Component
public class AuthenticationFacade {

	@Autowired
	private RepositorioUsuario usuarioRepositorio;

	public Usuario getUsuarioAutenticado() {
		// Obtendo o nome de usuário do contexto de segurança
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// Buscando o usuário autenticado no banco de dados
		return usuarioRepositorio.findByNome(username)
				.orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado"));
	}
}
