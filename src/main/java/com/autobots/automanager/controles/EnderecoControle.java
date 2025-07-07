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
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
 
    @Autowired
    private EnderecoRepositorio repositorio;

    @Autowired
    public ClienteSelecionador selecionador;

    @Autowired
    public ClienteRepositorio repositorioCliente;

    @GetMapping
	public List<Endereco> obterEnderecos(){
		List<Endereco> Enderecos = repositorio.findAll();
		return Enderecos;
	}
	
	@GetMapping("/cliente/{id}")
	public Endereco obterEnderecoCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorioCliente.findAll();
		return selecionador.selecionar(clientes, id).getEndereco();
	}
	
	@GetMapping("/{id}")
	public Endereco obterEndereco(@PathVariable long id) {
		Endereco Endereco = repositorio.findById(id).get();
		return Endereco;
	}
	
	@PostMapping("/cadastrar/{clienteId}")
	public void cadastrarEndereco(@RequestBody Endereco Endereco, @PathVariable long clienteId) {
		Cliente cliente = repositorioCliente.findById(clienteId).get();
		Endereco Enderecos = cliente.getEndereco();
		Enderecos = Endereco;
		repositorioCliente.save(cliente);
	}
	
	@PutMapping("/atualizar")
	public void atualizarEndereco(@RequestBody Endereco atualizacao) {
	    Endereco Endereco = repositorio.findById(atualizacao.getId())
	        .orElseThrow(() -> new RuntimeException("Esse Endereco não existe!"));

	    EnderecoAtualizador atualizador = new EnderecoAtualizador();
	    atualizador.atualizar(Endereco, atualizacao);
	    repositorio.save(Endereco);
	}

	
	@DeleteMapping("/excluir")
	public void excluirEndereco(@RequestBody Endereco exclusao) {
	    Endereco Endereco = repositorio.findById(exclusao.getId())
            .orElseThrow(() -> new RuntimeException("Esse Endereco não existe!"));

        repositorioCliente.findAll().forEach(cliente -> {
            if (cliente.getEndereco().equals(Endereco)) {
                cliente.setEndereco(null);
                repositorioCliente.save(cliente);
            }
        });

	    repositorio.delete(Endereco);
	}


}

