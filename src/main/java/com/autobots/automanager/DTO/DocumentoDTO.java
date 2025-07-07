package com.autobots.automanager.DTO;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.TipoDocumento;

public class DocumentoDTO extends RepresentationModel<DocumentoDTO> {
    private Long id;
    private TipoDocumento tipo;
    private Date dataEmissao;
    private String numero;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoDocumento getTipo() {
		return tipo;
	}
	public void setTipo(TipoDocumento tipo) {
		this.tipo = tipo;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
    
    
}