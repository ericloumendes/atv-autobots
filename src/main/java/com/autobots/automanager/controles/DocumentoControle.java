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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
 
    @Autowired
    private DocumentoRepositorio repositorio;

    @Autowired
    public ClienteSelecionador selecionador;

    @Autowired
    public ClienteRepositorio repositorioCliente;

    @GetMapping
	public List<Documento> obterDocumentos(){
		List<Documento> documentos = repositorio.findAll();
		return documentos;
	}
	
	@GetMapping("/cliente/{id}")
	public List<Documento> obterDocumentoCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorioCliente.findAll();
		return selecionador.selecionar(clientes, id).getDocumentos();
	}
	
	@GetMapping("/{id}")
	public Documento obterDocumento(@PathVariable long id) {
		Documento documento = repositorio.findById(id).get();
		return documento;
	}
	
	@PostMapping("/cadastrar/{clienteId}")
	public void cadastrarDocumento(@RequestBody Documento documento, @PathVariable long clienteId) {
		Cliente cliente = repositorioCliente.findById(clienteId).get();
		List<Documento> documentos = cliente.getDocumentos();
		documentos.add(documento);
		repositorioCliente.save(cliente);
	}
	
	@PutMapping("/atualizar")
	public void atualizarDocumento(@RequestBody Documento atualizacao) {
	    Documento documento = repositorio.findById(atualizacao.getId())
	        .orElseThrow(() -> new RuntimeException("Esse Documento não existe!"));

	    DocumentoAtualizador atualizador = new DocumentoAtualizador();
	    atualizador.atualizar(documento, atualizacao);
	    repositorio.save(documento);
	}

	
	@DeleteMapping("/excluir")
	public void excluirDocumento(@RequestBody Documento exclusao) {
	    Documento documento = repositorio.findById(exclusao.getId())
            .orElseThrow(() -> new RuntimeException("Esse Documento não existe!"));

        repositorioCliente.findAll().forEach(cliente -> {
            if (cliente.getDocumentos().contains(documento)) {
                cliente.getDocumentos().remove(documento);
                repositorioCliente.save(cliente);
            }
        });

	    repositorio.delete(documento);
	}


}
