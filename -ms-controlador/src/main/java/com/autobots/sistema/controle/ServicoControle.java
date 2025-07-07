package com.autobots.sistema.controle;

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

import com.autobots.sistema.DTO.ServicoDTO;
import com.autobots.sistema.DTO.VendaDTO;
import com.autobots.sistema.entidades.CredencialUsuarioSenha;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.entidades.Venda;
import com.autobots.sistema.modelo.AdicionadorLinkServico;
import com.autobots.sistema.repositorios.RepositorioUsuario;
import com.autobots.sistema.service.ServicoService;

@RestController
@RequestMapping("/servico")
public class ServicoControle {

    @Autowired
    private ServicoService service;

    @Autowired
    private AdicionadorLinkServico adicionadorLink;

    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/")
    public ResponseEntity<List<ServicoDTO>> obterServicos() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"));

            List<ServicoDTO> todosServicos = service.obterServicos();
            List<ServicoDTO> servicosFiltrados = todosServicos;

            if (isCliente) {
                Usuario usuario = usuarioRepositorio.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .filter(c -> c instanceof CredencialUsuarioSenha)
                                .map(c -> ((CredencialUsuarioSenha) c).getNomeUsuario())
                                .anyMatch(nome -> nome.equals(auth.getName())))
                        .findFirst()
                        .orElse(null);

                if (usuario == null) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

                // Coleta serviços de todas as vendas do cliente
                Set<Long> servicosDoCliente = new HashSet<>();
                for (Venda venda : usuario.getVendas()) {
                    if (venda.getServicos() != null) {
                        servicosDoCliente.addAll(
                            venda.getServicos().stream()
                                .map(servico -> servico.getId())
                                .collect(Collectors.toSet())
                        );
                    }
                }


                servicosFiltrados = todosServicos.stream()
                        .filter(s -> servicosDoCliente.contains(s.getId()))
                        .collect(Collectors.toList());
            }

            adicionadorLink.adicionadorLink(servicosFiltrados);

            if (servicosFiltrados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(servicosFiltrados);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> obterServico(@PathVariable Long id) {
        try {
            ServicoDTO servico = service.obterServico(id);
            if (servico == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"));

            if (isCliente) {
                Usuario usuario = usuarioRepositorio.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .filter(c -> c instanceof CredencialUsuarioSenha)
                                .map(c -> ((CredencialUsuarioSenha) c).getNomeUsuario())
                                .anyMatch(nome -> nome.equals(auth.getName())))
                        .findFirst()
                        .orElse(null);

                if (usuario == null) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

                boolean pertenceAoCliente = usuario.getVendas().stream()
                        .flatMap(v -> v.getServicos().stream())
                        .anyMatch(s -> s.getId().equals(servico.getId()));

                if (!pertenceAoCliente) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            adicionadorLink.adicionadorLink(servico);
            return ResponseEntity.status(HttpStatus.FOUND).body(servico);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar/empresa/{empresaId}")
    public ResponseEntity<ServicoDTO> cadastrarServicoEmpresa(@RequestBody ServicoDTO servico, @PathVariable Long empresaId) {
        try {
            ServicoDTO servicoCadastrado = service.cadastrarServicoEmpresa(servico, empresaId);
            if (servicoCadastrado != null) {
                return ResponseEntity.ok(servicoCadastrado);
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
    public ResponseEntity<ServicoDTO> atualizarServico(@RequestBody ServicoDTO servico) {
        try {
            ServicoDTO servicoAtualizado = service.atualizarServico(servico);
            if (servicoAtualizado != null) {
                return ResponseEntity.ok(servicoAtualizado);
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
    public ResponseEntity<Void> deletarServico(@RequestBody ServicoDTO servico) {
        try {
            service.deletarServico(servico);
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
