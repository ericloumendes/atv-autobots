package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

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
import com.autobots.automanager.modelo.AdicionadorLinkDocumento;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle{
	
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@Autowired
	private DocumentoRepositorio repositorio;
	
	@Autowired
	private ClienteSelecionador selecionador;
	
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;
	
	@GetMapping
	public ResponseEntity<List<Documento>> obterDocumentos(){
		List<Documento> documentos = repositorio.findAll();
		if (documentos.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionadorLink(documentos);
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<List<Documento>> obterDocumentoCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorioCliente.findAll();
		List<Documento> documentos = selecionador.selecionar(clientes, id).getDocumentos();
		if (documentos.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionadorLink(documentos);
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
		Documento documento = repositorio.findById(id).get();
		if(documento == null) {
			ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionadorLink(documento);
			ResponseEntity<Documento> resposta = new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PostMapping("/cadastrar/{clienteId}")
	public ResponseEntity<?> cadastrarDocumento(@RequestBody Documento documento, @PathVariable long clienteId) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(documento.getId() == null) {
			Cliente cliente = repositorioCliente.findById(clienteId).get();
			List<Documento> documentos = cliente.getDocumentos();
			documentos.add(documento);
			repositorioCliente.save(cliente);
			status = HttpStatus.CREATED;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarDocumento(@RequestBody Documento atualizacao) {
		HttpStatus status_code = null;
	    Documento documento = repositorio.findById(atualizacao.getId())
	        .orElseThrow(() -> new RuntimeException("Esse Documento não existe!"));
	    if(documento != null) {
		    DocumentoAtualizador atualizador = new DocumentoAtualizador();
		    atualizador.atualizar(documento, atualizacao);
		    repositorio.save(documento);
		    status_code = HttpStatus.OK;
	    } else {
			status_code = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status_code);
	}

	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirDocumento(@RequestBody Documento exclusao) {
	    Documento documento = repositorio.findById(exclusao.getId())
            .orElseThrow(() -> new RuntimeException("Esse Documento não existe!"));

		if (documento != null) {

        repositorioCliente.findAll().forEach(cliente -> {
            if (cliente.getDocumentos().contains(documento)) {
                cliente.getDocumentos().remove(documento);
                repositorioCliente.save(cliente);
            }
        });

	        repositorio.delete(documento);

	        return new ResponseEntity<>(HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
}
