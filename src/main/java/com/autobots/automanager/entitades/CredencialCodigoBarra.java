package com.autobots.automanager.entitades;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CredencialCodigoBarra extends Credencial {
	@Column(nullable = false, unique = true)
	private long codigo;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	@Override
	public boolean equals(Object o) {
    	if (this == o) return true;
    	if (!(o instanceof CredencialCodigoBarra)) return false;
    	if (!super.equals(o)) return false;
    	CredencialCodigoBarra that = (CredencialCodigoBarra) o;
    	return Objects.equals(codigo, that.codigo);
	}

	@Override
	public int hashCode() {
    	return Objects.hash(super.hashCode(), codigo);
	}
}