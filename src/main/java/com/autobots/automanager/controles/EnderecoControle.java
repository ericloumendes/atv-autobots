package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.AdicionadorLinkCliente;
import com.autobots.automanager.modelo.AdicionadorLinkEndereco;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@Autowired
	private EnderecoRepositorio repositorio;
	
	@Autowired
	private ClienteSelecionador selecionador;
	
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;
	
	@GetMapping
	public ResponseEntity<List<Endereco>> obterEnderecos(){
		List<Endereco> enderecos = repositorio.findAll();
		if (enderecos.isEmpty()) {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionadorLink(enderecos);
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<Endereco> obterEnderecoCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorioCliente.findAll();
		Endereco endereco = selecionador.selecionar(clientes, id).getEndereco();
		if (endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionadorLink(endereco);
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(endereco, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
		Endereco endereco = repositorio.findById(id).get();
		if(endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionadorLink(endereco);
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PostMapping("/cadastrar/{clienteId}")
	public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco, @PathVariable long clienteId) {
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente cliente = repositorioCliente.findById(clienteId).get();
		if(endereco.getId() == null) {
			cliente.setEndereco(endereco);
			repositorioCliente.save(cliente);
			status = HttpStatus.CREATED;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);

	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Endereco endereco = repositorio.findById(atualizacao.getId())
				.orElseThrow(() -> new RuntimeException("Esse endereço nao existe!"));
		
		if(endereco != null) {
	    	EnderecoAtualizador atualizador = new EnderecoAtualizador();
	    	atualizador.atualizar(endereco, atualizacao);
	    	repositorio.save(endereco);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirEndereco(@RequestBody Endereco exclusao) {
		Endereco endereco = repositorio.findById(exclusao.getId())
				.orElseThrow(() -> new RuntimeException("Esse endereço não existe!"));
		
		if(endereco != null) {
        	repositorioCliente.findAll().forEach(cliente -> {
            	if (cliente.getEndereco().equals(endereco)) {
                	cliente.setEndereco(null);
                	repositorioCliente.save(cliente);
            	}
        	});

			repositorio.delete(endereco);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
}
