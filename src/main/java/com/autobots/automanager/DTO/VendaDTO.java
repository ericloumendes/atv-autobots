package com.autobots.automanager.DTO;

import java.util.Date;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

public class VendaDTO extends RepresentationModel<VendaDTO>{
    private Long id;
    private Date cadastro;
    private String identificacao;
    private Long clienteId;
    private Long funcionarioId;
    private Long veiculoId;
    private Long empresaId; 
    private Set<MercadoriaDTO> mercadorias;
    private Set<ServicoDTO> servicos;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCadastro() {
		return cadastro;
	}
	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public Long getFuncionarioId() {
		return funcionarioId;
	}
	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}
	public Long getVeiculoId() {
		return veiculoId;
	}
	public void setVeiculoId(Long veiculoId) {
		this.veiculoId = veiculoId;
	}
	public Long getEmpresaId() {
		return empresaId;
	}
	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
	public Set<MercadoriaDTO> getMercadorias() {
		return mercadorias;
	}
	public void setMercadorias(Set<MercadoriaDTO> mercadorias) {
		this.mercadorias = mercadorias;
	}
	public Set<ServicoDTO> getServicos() {
		return servicos;
	}
	public void setServicos(Set<ServicoDTO> servicos) {
		this.servicos = servicos;
	}
    
    
}
