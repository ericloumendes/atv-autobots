package com.autobots.sistema.DTO;

import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.sistema.enumeracoes.Perfil;

public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
    private Long id;
    private String nome;
    private String nomeSocial;
    private Perfil perfil;
    private EnderecoDTO endereco;
    private Set<DocumentoDTO> documentos;
    private Set<EmailDTO> emails;
    private CredencialDTO credencial;
    private Set<TelefoneDTO> telefones;
    private Set<MercadoriaDTO> mercadorias;
    private Set<VendaDTO> vendas;
    private Set<VeiculoDTO> veiculos;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeSocial() {
		return nomeSocial;
	}
	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public EnderecoDTO getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}
	public Set<DocumentoDTO> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(Set<DocumentoDTO> documentos) {
		this.documentos = documentos;
	}
	public Set<EmailDTO> getEmails() {
		return emails;
	}
	public void setEmails(Set<EmailDTO> emails) {
		this.emails = emails;
	}
	public CredencialDTO getCredencial() {
		return credencial;
	}
	public void setCredencial(CredencialDTO credencial) {
		this.credencial = credencial;
	}
	public Set<TelefoneDTO> getTelefones() {
		return telefones;
	}
	public void setTelefones(Set<TelefoneDTO> telefones) {
		this.telefones = telefones;
	}
	public Set<MercadoriaDTO> getMercadorias() {
		return mercadorias;
	}
	public void setMercadorias(Set<MercadoriaDTO> mercadorias) {
		this.mercadorias = mercadorias;
	}
	public Set<VendaDTO> getVendas() {
		return vendas;
	}
	public void setVendas(Set<VendaDTO> vendas) {
		this.vendas = vendas;
	}
	public Set<VeiculoDTO> getVeiculos() {
		return veiculos;
	}
	public void setVeiculos(Set<VeiculoDTO> veiculos) {
		this.veiculos = veiculos;
	}
    
    
}
