package com.autobots.sistema.controle;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.autobots.sistema.DTO.DocumentoDTO;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.modelo.AdicionadorLinkDocumento;
import com.autobots.sistema.repositorios.RepositorioUsuario;
import com.autobots.sistema.service.DocumentoService;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {

    @Autowired
    private DocumentoService service;

    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/")
    public ResponseEntity<List<DocumentoDTO>> obterDocumentos() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));

            List<DocumentoDTO> todosDocumentos = service.obterDocumentos();
            List<DocumentoDTO> documentosFiltrados;

            if (isCliente) {
                Usuario usuario = repositorioUsuario.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                        .findFirst().orElse(null);

                if (usuario == null) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

                documentosFiltrados = todosDocumentos.stream()
                        .filter(doc -> usuario.getDocumentos().stream()
                                .anyMatch(d -> d.getId().equals(doc.getId())))
                        .collect(Collectors.toList());
            } else {
                documentosFiltrados = todosDocumentos;
            }

            adicionadorLink.adicionadorLink(documentosFiltrados);

            return documentosFiltrados.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                    : ResponseEntity.status(HttpStatus.FOUND).body(documentosFiltrados);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> obterDocumento(@PathVariable Long id) {
        try {
            DocumentoDTO documento = service.obterDocumento(id);
            if (documento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));

            if (isCliente) {
                Usuario usuario = repositorioUsuario.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                        .findFirst().orElse(null);

                if (usuario == null || usuario.getDocumentos().stream()
                        .noneMatch(d -> d.getId().equals(documento.getId()))) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            adicionadorLink.adicionadorLink(documento);
            return ResponseEntity.status(HttpStatus.FOUND).body(documento);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarDocumento(@RequestBody DocumentoDTO documento) {
        try {
            service.deletarDocumento(documento);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
