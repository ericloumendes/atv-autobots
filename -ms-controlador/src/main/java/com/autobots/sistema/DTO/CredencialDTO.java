package com.autobots.sistema.DTO;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

public class CredencialDTO extends RepresentationModel<CredencialDTO> {
    private Long id;
    private Date criacao;
    private Date ultimoAcesso;
    private boolean inativo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCriacao() {
		return criacao;
	}
	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}
	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}
	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}
	public boolean isInativo() {
		return inativo;
	}
	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}
    
    
}