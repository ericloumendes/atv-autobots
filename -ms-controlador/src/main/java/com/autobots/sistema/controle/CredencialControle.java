package com.autobots.sistema.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.autobots.sistema.DTO.CredencialDTO;
import com.autobots.sistema.DTO.CredencialUsuarioSenhaDTO;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.modelo.AdicionadorLinkCredencial;
import com.autobots.sistema.repositorios.RepositorioUsuario;
import com.autobots.sistema.service.CredencialService;

@RestController
@RequestMapping("/credencial")
public class CredencialControle {

    @Autowired
    private CredencialService service;

    @Autowired
    private AdicionadorLinkCredencial adicionadorLink;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<CredencialDTO>> obterCredenciais() {
        try {
            List<CredencialDTO> credenciais = service.obterCredenciais();
            adicionadorLink.adicionadorLink(credenciais);
            if (credenciais != null && !credenciais.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(credenciais);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<CredencialDTO> obterCredencial(@PathVariable Long id) {
        try {
            CredencialDTO credencial = service.obterCredencial(id);
            if (credencial == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                // Verifica se a credencial pertence ao usuário autenticado
                Usuario usuario = repositorioUsuario.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                        .findFirst().orElse(null);

                boolean pertenceAoUsuario = usuario != null && usuario.getCredenciais().stream()
                        .anyMatch(c -> c.getId().equals(id));

                if (!pertenceAoUsuario) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            adicionadorLink.adicionadorLink(credencial);
            return ResponseEntity.status(HttpStatus.FOUND).body(credencial);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/cadastrar/usuario")
    public ResponseEntity<CredencialDTO> cadastrarCredencialUsuario(@RequestBody CredencialUsuarioSenhaDTO credencialDTO) {
        try {
            CredencialDTO credencialCadastrada = service.cadastrarCredencialUsuario(credencialDTO);
            return ResponseEntity.ok(credencialCadastrada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/atualizar")
    public ResponseEntity<CredencialDTO> atualizarCredencial(@RequestBody CredencialDTO credencialDTO) {
        try {
            CredencialDTO credencialAtualizada = service.atualizarCredencial(credencialDTO);
            return ResponseEntity.ok(credencialAtualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarCredencial(@RequestBody CredencialDTO credencialDTO) {
        try {
            service.deletarCredencial(credencialDTO);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
