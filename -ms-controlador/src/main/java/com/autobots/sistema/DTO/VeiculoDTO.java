package com.autobots.sistema.DTO;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.sistema.enumeracoes.TipoVeiculo;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VeiculoDTO extends RepresentationModel<VeiculoDTO> {
    private Long id;
    private TipoVeiculo tipo;
    private String modelo;
    private String placa;
    private Long proprietarioId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoVeiculo getTipo() {
		return tipo;
	}
	public void setTipo(TipoVeiculo tipo) {
		this.tipo = tipo;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Long getProprietarioId() {
		return proprietarioId;
	}
	public void setProprietarioId(Long proprietarioId) {
		this.proprietarioId = proprietarioId;
	}
    
    
}