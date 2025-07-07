package com.autobots.sistema.jwt;

import java.util.Date;

import com.autobots.sistema.enumeracoes.Perfil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class GeradorJwt {
	private String assinatura;
	private Date expiracao;

	public GeradorJwt(String assinatura, long duracao) {
		this.assinatura = assinatura;
		this.expiracao = new Date(System.currentTimeMillis() + duracao);
	}

	public String gerarJwt(String nomeUsuario, Perfil roles) {
		return Jwts.builder()
				.setSubject(nomeUsuario) // Nome do usuário
				.claim("roles", roles)  // Adiciona as roles ao payload
				.setExpiration(this.expiracao) // Data de expiração
				.signWith(SignatureAlgorithm.HS512, this.assinatura.getBytes()) // Assinatura
				.compact();
	}
}