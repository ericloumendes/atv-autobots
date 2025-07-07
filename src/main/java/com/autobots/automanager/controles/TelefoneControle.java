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
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.AdicionadorLinkCliente;
import com.autobots.automanager.modelo.AdicionadorLinkTelefone;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {

    @Autowired
    private ClienteRepositorio repositorioCliente;

    @Autowired
    private TelefoneRepositorio repositorio;

    @Autowired
    private ClienteSelecionador selecionador;
    
	@Autowired
	private AdicionadorLinkTelefone adicionadorLink;

    @GetMapping
    public ResponseEntity<List<Telefone>> obterTelefones() {
    	List<Telefone> telefones = repositorio.findAll();
		if (telefones.isEmpty()) {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionadorLink(telefones);
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
			return resposta;
		}
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<Telefone>> obterTelefoneCliente(@PathVariable long id) {
        List<Cliente> clientes = repositorioCliente.findAll();
        List<Telefone> telefones = selecionador.selecionar(clientes, id).getTelefones();
        if (telefones.isEmpty()) {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionadorLink(telefones);
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
			return resposta;
		}
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
    	Telefone telefone = repositorio.findById(id).get();
		if(telefone == null) {
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionadorLink(telefone);
			ResponseEntity<Telefone> resposta = new ResponseEntity<Telefone>(telefone, HttpStatus.FOUND);
			return resposta;
		}
	}

    @PostMapping("/cadastrar/{clienteId}")
    public ResponseEntity<?> adicionarTelefone(@PathVariable long clienteId, @RequestBody Telefone telefone) {
    	HttpStatus status = HttpStatus.CONFLICT;
    	if(telefone.getId() == null) {
            Cliente cliente = repositorioCliente.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Esse cliente não existe!"));

            cliente.getTelefones().add(telefone);
            repositorioCliente.save(cliente);
            status = HttpStatus.CREATED;
    	} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone atualizacao) {
    	HttpStatus status = HttpStatus.CONFLICT;
        Telefone telefone = repositorio.findById(atualizacao.getId())
            .orElseThrow(() -> new RuntimeException("Esse felefone não existe!"));

        if(telefone != null) {
	        TelefoneAtualizador atualizador = new TelefoneAtualizador();

            atualizador.atualizar(telefone, atualizacao);
            repositorio.save(telefone);
            status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirTelefone(@RequestBody Telefone telefoneExcluir) {
        Telefone telefone = repositorio.findById(telefoneExcluir.getId())
            .orElseThrow(() -> new RuntimeException("Esse telefone não existe!"));

        if(telefone != null) {
        repositorioCliente.findAll().forEach(cliente -> {
            if (cliente.getTelefones().contains(telefone)) {
                cliente.getTelefones().remove(telefone);
                repositorioCliente.save(cliente);
            }
        });
                return new ResponseEntity<>(HttpStatus.OK);
        } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
    }

}
