package com.autobots.sistema.DTO;

import org.springframework.hateoas.RepresentationModel;

public class EmailDTO extends RepresentationModel<EmailDTO> {
    private Long id;
    private String endereco;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
    
    
}
