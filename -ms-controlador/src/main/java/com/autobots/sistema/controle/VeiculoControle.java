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

import com.autobots.sistema.DTO.VeiculoDTO;
import com.autobots.sistema.entidades.Credencial;
import com.autobots.sistema.entidades.CredencialUsuarioSenha;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.modelo.AdicionadorLinkVeiculo;
import com.autobots.sistema.repositorios.RepositorioUsuario;
import com.autobots.sistema.service.VeiculoService;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {

    @Autowired
    private VeiculoService service;

    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    @Autowired
    private AdicionadorLinkVeiculo adicionadorLink;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/")
    public ResponseEntity<List<VeiculoDTO>> obterVeiculos() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"));

            List<VeiculoDTO> todosVeiculos = service.obterVeiculos();
            List<VeiculoDTO> veiculosFiltrados = todosVeiculos;

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

                veiculosFiltrados = todosVeiculos.stream()
                        .filter(v -> v.getProprietarioId() != null && v.getProprietarioId().equals(usuarioId))
                        .collect(Collectors.toList());
            }

            adicionadorLink.adicionadorLink(veiculosFiltrados);

            if (veiculosFiltrados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.FOUND).body(veiculosFiltrados);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> obterVeiculo(@PathVariable Long id) {
        try {
            VeiculoDTO veiculo = service.obterVeiculo(id);
            if (veiculo == null) {
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

                if (usuario == null || !usuario.getId().equals(veiculo.getProprietarioId())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            adicionadorLink.adicionadorLink(veiculo);
            return ResponseEntity.status(HttpStatus.FOUND).body(veiculo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<VeiculoDTO> cadastrarVeiculo(@RequestBody VeiculoDTO veiculo,
                                                        @PathVariable Long usuarioId) {
        try {
            VeiculoDTO veiculoCadastrado = service.cadastrarVeiculo(veiculo, usuarioId);
            if (veiculoCadastrado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(veiculoCadastrado);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PutMapping("/atualizar")
    public ResponseEntity<VeiculoDTO> atualizarVeiculo(@RequestBody VeiculoDTO veiculo) {
        try {
            VeiculoDTO veiculoAtualizado = service.atualizarVeiculo(veiculo);
            if (veiculoAtualizado != null) {
                return ResponseEntity.ok(veiculoAtualizado);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarVeiculo(@RequestBody VeiculoDTO veiculo) {
        try {
            service.deletarVeiculo(veiculo);
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
