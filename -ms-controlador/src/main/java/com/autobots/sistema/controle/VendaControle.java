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

import com.autobots.sistema.DTO.VendaDTO;
import com.autobots.sistema.entidades.CredencialUsuarioSenha;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.repositorios.RepositorioUsuario;
import com.autobots.sistema.service.VendaService;

@RestController
@RequestMapping("/venda")
public class VendaControle {

    @Autowired
    private VendaService service;

    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/")
    public ResponseEntity<List<VendaDTO>> obterVendas() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"));

            List<VendaDTO> todasVendas = service.obterVendas();
            List<VendaDTO> vendasFiltradas = todasVendas;

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

                Long usuarioId = usuario.getId();

                vendasFiltradas = todasVendas.stream()
                        .filter(v -> v.getClienteId() != null && v.getClienteId().equals(usuarioId))
                        .collect(Collectors.toList());
            }

            if (vendasFiltradas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(vendasFiltradas);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> obterVenda(@PathVariable Long id) {
        try {
            VendaDTO venda = service.obterVenda(id);
            if (venda == null) {
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

                if (usuario == null || !usuario.getId().equals(venda.getClienteId())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(venda);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<VendaDTO> cadastrarVendaUsuario(@RequestBody VendaDTO venda, @PathVariable Long usuarioId) {
        try {
            VendaDTO vendaCadastrada = service.cadastrarVendaUsuario(venda, usuarioId);
            if (vendaCadastrada != null) {
                return ResponseEntity.ok(vendaCadastrada);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar/empresa/{empresaId}")
    public ResponseEntity<VendaDTO> cadastrarVendaEmpresa(@RequestBody VendaDTO venda, @PathVariable Long empresaId) {
        try {
            VendaDTO vendaCadastrada = service.cadastrarVendaEmpresa(venda, empresaId);
            if (vendaCadastrada != null) {
                return ResponseEntity.ok(vendaCadastrada);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PutMapping("/atualizar")
    public ResponseEntity<VendaDTO> atualizarVenda(@RequestBody VendaDTO venda) {
        try {
            VendaDTO vendaAtualizada = service.atualizarVenda(venda);
            if (vendaAtualizada != null) {
                return ResponseEntity.ok(vendaAtualizada);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarVenda(@RequestBody VendaDTO venda) {
        try {
            service.deletarVenda(venda);
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
