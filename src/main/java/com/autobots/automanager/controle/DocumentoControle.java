package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.DTO.DocumentoDTO;
import com.autobots.automanager.modelo.AdicionadorLinkDocumento;
import com.autobots.automanager.service.DocumentoService;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {

    @Autowired
    private DocumentoService service;
    
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;

    @GetMapping("/")
    public ResponseEntity<List<DocumentoDTO>> obterDocumentos() {
        try {
            List<DocumentoDTO> documentos = service.obterDocumentos();
            adicionadorLink.adicionadorLink(documentos);
            if (documentos != null && !documentos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(documentos);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> obterDocumento(@PathVariable Long id) {
        try {
            DocumentoDTO documento = service.obterDocumento(id);
            adicionadorLink.adicionadorLink(documento);
            if (documento != null) {
                return ResponseEntity.status(HttpStatus.FOUND).body(documento);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<DocumentoDTO> cadastrarDocumento(@RequestBody DocumentoDTO documento, @PathVariable Long usuarioId) {
        try {
        	DocumentoDTO documentoCadastrado = service.cadastrarDocumento(documento, usuarioId);
            if (documentoCadastrado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(documentoCadastrado);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<DocumentoDTO> atualizarDocumento(@RequestBody DocumentoDTO documento) {
        try {
            DocumentoDTO documentoAtualizado = service.atualizarDocumento(documento);
            if (documentoAtualizado != null) {
                return ResponseEntity.ok(documentoAtualizado);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarDocumento(@RequestBody DocumentoDTO documento) {
        try {
            service.deletarDocumento(documento);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
