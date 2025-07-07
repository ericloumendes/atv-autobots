package com.autobots.automanager.controle;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.DTO.EmailDTO;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.AdicionadorLinkEmail;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailControle {

    @Autowired
    private EmailService service;

    @Autowired
    private AdicionadorLinkEmail adicionador;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/")
    public ResponseEntity<List<EmailDTO>> obterEmails() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));

            List<EmailDTO> todosEmails = service.obterEmails();
            List<EmailDTO> filtrados;

            if (isCliente) {
                Usuario usuario = repositorioUsuario.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                        .findFirst().orElse(null);

                if (usuario == null) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

                filtrados = todosEmails.stream()
                        .filter(email -> usuario.getEmails().stream()
                                .anyMatch(e -> e.getId().equals(email.getId())))
                        .collect(Collectors.toList());

            } else {
                filtrados = todosEmails;
            }

            adicionador.adicionadorLink(filtrados);
            return filtrados.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                    : ResponseEntity.status(HttpStatus.FOUND).body(filtrados);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<EmailDTO> obterEmail(@PathVariable Long id) {
        try {
            EmailDTO email = service.obterEmail(id);
            if (email == null) {
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

                if (usuario == null || usuario.getEmails().stream()
                        .noneMatch(e -> e.getId().equals(email.getId()))) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            adicionador.adicionadorLink(email);
            return ResponseEntity.status(HttpStatus.FOUND).body(email);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<EmailDTO> cadastrarEmail(@RequestBody EmailDTO email, @PathVariable Long usuarioId) {
        try {
            EmailDTO emailCadastrado = service.cadastrarEmail(email, usuarioId);
            if (emailCadastrado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(emailCadastrado);
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
    public ResponseEntity<EmailDTO> atualizarEmail(@RequestBody EmailDTO email) {
        try {
            EmailDTO emailAtualizado = service.atualizarEmail(email);
            if (emailAtualizado != null) {
                return ResponseEntity.ok(emailAtualizado);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarEmail(@RequestBody EmailDTO email) {
        try {
            service.deletarEmail(email);
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
