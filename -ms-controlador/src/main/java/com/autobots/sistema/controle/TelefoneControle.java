package com.autobots.sistema.controle;

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

import com.autobots.sistema.DTO.TelefoneDTO;
import com.autobots.sistema.entidades.CredencialUsuarioSenha;
import com.autobots.sistema.entidades.Telefone;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.modelo.AdicionadorLinkTelefone;
import com.autobots.sistema.repositorios.RepositorioUsuario;
import com.autobots.sistema.service.TelefoneService;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {

    @Autowired
    private TelefoneService service;

    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/")
    public ResponseEntity<List<TelefoneDTO>> obterTelefones() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"));

            List<TelefoneDTO> todosTelefones = service.obterTelefones();
            List<TelefoneDTO> telefonesFiltrados = todosTelefones;

            if (isCliente) {
                Usuario usuario = usuarioRepositorio.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .filter(c -> c instanceof CredencialUsuarioSenha)
                                .map(c -> (CredencialUsuarioSenha) c)
                                .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                        .findFirst()
                        .orElse(null);

                if (usuario == null) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

                Set<Long> telefonesDoUsuario = usuario.getTelefones().stream()
                        .map(Telefone::getId)
                        .collect(Collectors.toSet());

                telefonesFiltrados = todosTelefones.stream()
                        .filter(t -> telefonesDoUsuario.contains(t.getId()))
                        .collect(Collectors.toList());
            }

            adicionadorLink.adicionadorLink(telefonesFiltrados);

            if (telefonesFiltrados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(telefonesFiltrados);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<TelefoneDTO> obterTelefone(@PathVariable Long id) {
        try {
            TelefoneDTO telefone = service.obterTelefone(id);
            if (telefone == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"));

            if (isCliente) {
                Usuario usuario = usuarioRepositorio.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .filter(c -> c instanceof CredencialUsuarioSenha)
                                .map(c -> (CredencialUsuarioSenha) c)
                                .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                        .findFirst()
                        .orElse(null);

                if (usuario == null ||
                        usuario.getTelefones().stream().noneMatch(t -> t.getId().equals(id))) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            adicionadorLink.adicionadorLink(telefone);
            return ResponseEntity.status(HttpStatus.FOUND).body(telefone);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar/{usuarioId}")
    public ResponseEntity<TelefoneDTO> cadastrarCliente(@RequestBody TelefoneDTO telefone, @PathVariable Long usuarioId) {
        try {
            TelefoneDTO telefoneAtualizado = service.cadastrarTelefone(telefone, usuarioId);
            if (telefoneAtualizado != null) {
                return ResponseEntity.ok(telefoneAtualizado);
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
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@RequestBody TelefoneDTO telefone) {
        try {
            TelefoneDTO telefoneAtualizado = service.atualizarTelefone(telefone);
            if (telefoneAtualizado != null) {
                return ResponseEntity.ok(telefoneAtualizado);
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
    public ResponseEntity<Void> deletarTelefone(@RequestBody TelefoneDTO telefone) {
        try {
            service.deletarTelefone(telefone);
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
