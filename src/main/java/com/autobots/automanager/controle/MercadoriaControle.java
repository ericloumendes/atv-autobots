package com.autobots.automanager.controle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.AdicionadorLinkMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.service.MercadoriaService;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {

    @Autowired
    private MercadoriaService service;

    @Autowired
    private AdicionadorLinkMercadoria adicionador;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/")
    public ResponseEntity<List<MercadoriaDTO>> obterMercadorias() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));

            List<MercadoriaDTO> todas = service.obterMercadorias();
            List<MercadoriaDTO> filtradas;

            if (isCliente) {
                Usuario usuario = repositorioUsuario.findAll().stream()
                    .filter(u -> u.getCredenciais().stream()
                        .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                    .findFirst()
                    .orElse(null);

                if (usuario == null) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

                Set<Long> idsCliente = usuario.getMercadorias().stream()
                        .map(Mercadoria::getId)
                        .collect(Collectors.toSet());

                filtradas = todas.stream()
                        .filter(m -> idsCliente.contains(m.getId()))
                        .collect(Collectors.toList());
            } else {
                filtradas = todas;
            }

            adicionador.adicionadorLink(filtradas);
            return filtradas.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                    : ResponseEntity.status(HttpStatus.FOUND).body(filtradas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<MercadoriaDTO> obterMercadoria(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));

            MercadoriaDTO mercadoria = service.obterMercadoria(id);
            if (mercadoria == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (isCliente) {
                Usuario usuario = repositorioUsuario.findAll().stream()
                    .filter(u -> u.getCredenciais().stream()
                        .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                    .findFirst()
                    .orElse(null);

                if (usuario == null || usuario.getMercadorias().stream()
                        .noneMatch(m -> m.getId().equals(mercadoria.getId()))) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            adicionador.adicionadorLink(mercadoria);
            return ResponseEntity.status(HttpStatus.FOUND).body(mercadoria);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<MercadoriaDTO> cadastrarMercadoriaUsuario(@RequestBody MercadoriaDTO mercadoria, @PathVariable Long usuarioId) {
        try {
            MercadoriaDTO mercadoriaCadastrada = service.cadastrarMercadoriaUsuario(mercadoria, usuarioId);
            if (mercadoriaCadastrada != null) {
                return ResponseEntity.ok(mercadoriaCadastrada);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar/empresa/{empresaId}")
    public ResponseEntity<MercadoriaDTO> cadastrarMercadoriaEmpresa(@RequestBody MercadoriaDTO mercadoria, @PathVariable Long empresaId) {
        try {
            MercadoriaDTO mercadoriaCadastrada = service.cadastrarMercadoriaEmpresa(mercadoria, empresaId);
            if (mercadoriaCadastrada != null) {
                return ResponseEntity.ok(mercadoriaCadastrada);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PutMapping("/atualizar")
    public ResponseEntity<MercadoriaDTO> atualizarMercadoria(@RequestBody MercadoriaDTO mercadoria) {
        try {
            MercadoriaDTO mercadoriaAtualizado = service.atualizarMercadoria(mercadoria);
            if (mercadoriaAtualizado != null) {
                return ResponseEntity.ok(mercadoriaAtualizado);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarMercadoria(@RequestBody MercadoriaDTO mercadoria) {
        try {
            service.deletarMercadoria(mercadoria);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
