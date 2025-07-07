package com.autobots.sistema.entidades;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CredencialUsuarioSenha extends Credencial {
	@Column(nullable = false, unique = true)
	private String nomeUsuario;
	@Column(nullable = false)
	private String senha;

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CredencialUsuarioSenha)) return false;
        if (!super.equals(o)) return false;

        CredencialUsuarioSenha credencial = (CredencialUsuarioSenha) o;
        return Objects.equals(nomeUsuario, credencial.nomeUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nomeUsuario, senha);
    }

	public CredencialUsuarioSenha() {
		// Define a data de criação automaticamente
		this.setCriacao(new Date());
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
