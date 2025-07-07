package com.autobots.automanager.filtros;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autobots.automanager.adaptadores.UserDetailsImpl;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.jwt.ProvedorJwt;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Autenticador extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager gerenciadorAutenticacao;
	private final ProvedorJwt provedorJwt;

	public Autenticador(AuthenticationManager gerenciadorAutenticacao, ProvedorJwt provedorJwt) {
		this.gerenciadorAutenticacao = gerenciadorAutenticacao;
		this.provedorJwt = provedorJwt;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		CredencialUsuarioSenha credencial = null;
		try {
			credencial = new ObjectMapper().readValue(request.getInputStream(), CredencialUsuarioSenha.class);
		} catch (IOException e) {
			credencial = new CredencialUsuarioSenha();
			credencial.setNomeUsuario("");
			credencial.setSenha("");
		}
		UsernamePasswordAuthenticationToken dadosAutenticacao = new UsernamePasswordAuthenticationToken(
				credencial.getNomeUsuario(), credencial.getSenha(), new ArrayList<>());
		return gerenciadorAutenticacao.authenticate(dadosAutenticacao);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication autenticacao) throws IOException, ServletException {
		UserDetailsImpl usuario = (UserDetailsImpl) autenticacao.getPrincipal();
		String nomeUsuario = usuario.getUsername();
		String jwt = provedorJwt.proverJwt(nomeUsuario, usuario.getPerfil());
		response.addHeader("Authorization", "Bearer " + jwt);
	}
}