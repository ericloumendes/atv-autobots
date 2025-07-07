package com.autobots.sistema.filtros;

class ValidadorCabecalho {
	private String cabecalho;

	public ValidadorCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}

	public boolean validar() {
		return cabecalho != null && cabecalho.startsWith("Bearer ");
	}

}