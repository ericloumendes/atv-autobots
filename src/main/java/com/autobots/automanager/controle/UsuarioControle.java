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

import com.autobots.automanager.DTO.UsuarioDTO;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {

    @Autowired
    private UsuarioService service;

    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/")
    public ResponseEntity<List<UsuarioDTO>> obterUsuarios() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"));

            List<UsuarioDTO> usuarios = service.obterClientes();

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

                usuarios = usuarios.stream()
                        .filter(u -> u.getId().equals(usuario.getId()))
                        .collect(Collectors.toList());
            }

            if (usuarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(usuarios);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterUsuario(@PathVariable Long id) {
        try {
            UsuarioDTO usuarioDTO = service.obterCliente(id);
            if (usuarioDTO == null) {
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

                if (usuario == null || !usuario.getId().equals(usuarioDTO.getId())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(usuarioDTO);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuario) {
        try {
            UsuarioDTO usuarioCadastrado = service.cadastrarCliente(usuario);
            if (usuarioCadastrado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
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
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioDTO usuario) {
        try {
            UsuarioDTO usuarioAtualizado = service.atualizarCliente(usuario);
            if (usuarioAtualizado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
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
    public ResponseEntity<Void> deletarUsuario(@RequestBody UsuarioDTO usuario) {
        try {
            service.deletarCliente(usuario);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
