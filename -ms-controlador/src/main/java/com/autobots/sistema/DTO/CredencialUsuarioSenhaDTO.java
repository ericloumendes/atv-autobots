package com.autobots.sistema.DTO;

import java.util.Objects;

import com.autobots.sistema.entidades.CredencialUsuarioSenha;

public class CredencialUsuarioSenhaDTO extends CredencialDTO {
    private String nomeUsuario;
    private String senha;

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CredencialUsuarioSenha)) return false;
        if (!super.equals(o)) return false;

        CredencialUsuarioSenha credencial = (CredencialUsuarioSenha) o;
        return Objects.equals(nomeUsuario, credencial.getNomeUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nomeUsuario, senha);
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
