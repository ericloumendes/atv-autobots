package com.autobots.automanager.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.autobots.automanager.enumeracoes.Perfil;

import io.jsonwebtoken.Claims;

@Component
public class ProvedorJwt {
	@Value("${jwt.secret}")
	private String assinatura;
	@Value("${jwt.expiration}")
	private Long duracao;

	private GeradorJwt gerador;
	private AnalisadorJwt analisador;
	private ValidadorJwt validador;

	public String proverJwt(String nomeUsuario, Perfil roles) {
		gerador = new GeradorJwt(assinatura, duracao);
		return gerador.gerarJwt(nomeUsuario, roles);
	}

	public boolean validarJwt(String jwt) {
		analisador = new AnalisadorJwt(assinatura, jwt);
		validador = new ValidadorJwt();
		return validador.validar(analisador.obterReivindicacoes());
	}

	public String obterNomeUsuario(String jwt) {
		analisador = new AnalisadorJwt(assinatura, jwt);
		Claims reivindicacoes = analisador.obterReivindicacoes();
		return analisador.obterNomeUsuairo(reivindicacoes);
	}

	public String getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(String assinatura) {
		this.assinatura = assinatura;
	}

	public Long getDuracao() {
		return duracao;
	}

	public void setDuracao(Long duracao) {
		this.duracao = duracao;
	}

	public GeradorJwt getGerador() {
		return gerador;
	}

	public void setGerador(GeradorJwt gerador) {
		this.gerador = gerador;
	}

	public AnalisadorJwt getAnalisador() {
		return analisador;
	}

	public void setAnalisador(AnalisadorJwt analisador) {
		this.analisador = analisador;
	}

	public ValidadorJwt getValidador() {
		return validador;
	}

	public void setValidador(ValidadorJwt validador) {
		this.validador = validador;
	}
	
	
}