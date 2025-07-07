package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {

    @Autowired
    private TelefoneRepositorio repositorio;

    @Autowired
    public ClienteSelecionador selecionador;

    @Autowired
    public ClienteRepositorio repositorioCliente;

    @GetMapping
	public List<Telefone> obterTelefones(){
		List<Telefone> telefones = repositorio.findAll();
		return telefones;
	}
	
	@GetMapping("/cliente/{id}")
	public List<Telefone> obterTelefoneCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorioCliente.findAll();
		return selecionador.selecionar(clientes, id).getTelefones();
	}
	
	@GetMapping("/{id}")
	public Telefone obterTelefone(@PathVariable long id) {
		Telefone telefone = repositorio.findById(id).get();
		return telefone;
	}
	
	@PostMapping("/cadastrar/{clienteId}")
	public void cadastrarTelefone(@RequestBody Telefone telefone, @PathVariable long clienteId) {
		Cliente cliente = repositorioCliente.findById(clienteId).get();
		List<Telefone> telefones = cliente.getTelefones();
		telefones.add(telefone);
		repositorioCliente.save(cliente);
	}
	
	@PutMapping("/atualizar")
	public void atualizarTelefone(@RequestBody Telefone atualizacao) {
	    Telefone telefone = repositorio.findById(atualizacao.getId())
	        .orElseThrow(() -> new RuntimeException("Esse Telefone não existe!"));

	    TelefoneAtualizador atualizador = new TelefoneAtualizador();
	    atualizador.atualizar(telefone, atualizacao);
	    repositorio.save(telefone);
	}

	
	@DeleteMapping("/excluir")
	public void excluirTelefone(@RequestBody Telefone exclusao) {
	    Telefone telefone = repositorio.findById(exclusao.getId())
            .orElseThrow(() -> new RuntimeException("Esse Telefone não existe!"));

        repositorioCliente.findAll().forEach(cliente -> {
            if (cliente.getTelefones().contains(telefone)) {
                cliente.getTelefones().remove(telefone);
                repositorioCliente.save(cliente);
            }
        });

	    repositorio.delete(telefone);
	}


}