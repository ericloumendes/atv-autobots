package com.autobots.sistema.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.autobots.sistema.DTO.EnderecoDTO;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.modelo.AdicionadorLinkEndereco;
import com.autobots.sistema.repositorios.RepositorioUsuario;
import com.autobots.sistema.service.EnderecoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/")
    public ResponseEntity<List<EnderecoDTO>> obterEnderecos() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));

            List<EnderecoDTO> todosEnderecos = enderecoService.obterEnderecos();
            List<EnderecoDTO> filtrados;

            if (isCliente) {
                Usuario usuario = repositorioUsuario.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                        .findFirst()
                        .orElse(null);

                if (usuario == null || usuario.getEndereco() == null) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

                Long idEnderecoUsuario = usuario.getEndereco().getId();
                filtrados = todosEnderecos.stream()
                        .filter(e -> e.getId().equals(idEnderecoUsuario))
                        .collect(Collectors.toList());

            } else {
                filtrados = todosEnderecos;
            }

            adicionadorLink.adicionadorLink(filtrados);
            return filtrados.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                    : ResponseEntity.status(HttpStatus.FOUND).body(filtrados);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> obterEndereco(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isCliente = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));

            EnderecoDTO endereco = enderecoService.obterEndereco(id);
            if (endereco == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (isCliente) {
                Usuario usuario = repositorioUsuario.findAll().stream()
                        .filter(u -> u.getCredenciais().stream()
                                .anyMatch(c -> c.getNomeUsuario().equals(auth.getName())))
                        .findFirst()
                        .orElse(null);

                if (usuario == null || usuario.getEndereco() == null ||
                        !usuario.getEndereco().getId().equals(endereco.getId())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            adicionadorLink.adicionadorLink(endereco);
            return ResponseEntity.status(HttpStatus.FOUND).body(endereco);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(@RequestBody EnderecoDTO enderecoDTO, @PathVariable Long usuarioId) {
        try {
            EnderecoDTO enderecoCadastrado = enderecoService.cadastrarEndereco(enderecoDTO, usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(enderecoCadastrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PutMapping("/atualizar")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        try {
            EnderecoDTO enderecoAtualizado = enderecoService.atualizarEndereco(enderecoDTO);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        try {
            enderecoService.deletarEndereco(enderecoDTO.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
