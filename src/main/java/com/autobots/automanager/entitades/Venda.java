package com.autobots.automanager.entitades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date cadastro;

    @Column(nullable = false, unique = true)
    private String identificacao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonBackReference
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonBackReference
    private Usuario funcionario;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(
        name = "venda_mercadoria",
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "mercadoria_id")
    )
    private Set<Mercadoria> mercadorias = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(
        name = "venda_servico",
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private Set<Servico> servicos = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonBackReference
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private Empresa empresa;

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

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Usuario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Usuario funcionario) {
		this.funcionario = funcionario;
	}

	public Set<Mercadoria> getMercadorias() {
		return mercadorias;
	}

	public void setMercadorias(Set<Mercadoria> mercadorias) {
		this.mercadorias = mercadorias;
	}

	public Set<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(Set<Servico> servicos) {
		this.servicos = servicos;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
    
    
}
