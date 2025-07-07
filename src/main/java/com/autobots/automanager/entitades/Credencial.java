package com.autobots.automanager.entitades;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Credencial {
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date criacao;
	@Column()
	private Date ultimoAcesso;
	@Column(nullable = false)
	private boolean inativo;

	@Override
	public boolean equals(Object o) {
    	if (this == o) return true;
    	if (!(o instanceof Credencial)) return false;
    	if (!super.equals(o)) return false;
    	Credencial that = (Credencial) o;
    	return Objects.equals(criacao, that.criacao);
	}

	@Override
	public int hashCode() {
    	return Objects.hash(super.hashCode(), id, criacao, ultimoAcesso, inativo);
	}

	
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