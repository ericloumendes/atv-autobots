package com.autobots.automanager.DTO;

import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
    private Long id;
    private String nome;
    private String nomeSocial;
    private Set<PerfilUsuario> perfis;
    private EnderecoDTO endereco;
    private Set<DocumentoDTO> documentos;
    private Set<EmailDTO> emails;
    private Set<CredencialDTO> credenciais;
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
	public Set<PerfilUsuario> getPerfis() {
		return perfis;
	}
	public void setPerfis(Set<PerfilUsuario> perfis) {
		this.perfis = perfis;
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
	public Set<CredencialDTO> getCredenciais() {
		return credenciais;
	}
	public void setCredenciais(Set<CredencialDTO> credenciais) {
		this.credenciais = credenciais;
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
