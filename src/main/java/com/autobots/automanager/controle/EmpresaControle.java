package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.DTO.EmpresaDTO;
import com.autobots.automanager.modelo.AdicionadorLinkEmpresa;
import com.autobots.automanager.service.EmpresaService;

@RestController
@RequestMapping("/empresa")
public class EmpresaControle {

    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private AdicionadorLinkEmpresa adicionadorLink;

    // Endpoint para obter todos as empresas
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @GetMapping("/")
    public ResponseEntity<List<EmpresaDTO>> obterEmpresas() {
        try {
            List<EmpresaDTO> empresas = empresaService.obterEmpresas();
            adicionadorLink.adicionadorLink(empresas);
            if (empresas != null && !empresas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(empresas);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para obter um único empresa por id
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> obterEmpresa(@PathVariable Long id) {
        try {
            EmpresaDTO empresa = empresaService.obterEmpresa(id);
            adicionadorLink.adicionadorLink(empresa);
            if (empresa != null) {
                return ResponseEntity.status(HttpStatus.FOUND).body(empresa);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para cadastrar uma novo empresa
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @PostMapping("/cadastrar")
    public ResponseEntity<EmpresaDTO> cadastrarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        try {
            EmpresaDTO empresaCadastrado = empresaService.cadastrarEmpresa(empresaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(empresaCadastrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para atualizar uma empresa
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE')")
    @PutMapping("/atualizar")
    public ResponseEntity<EmpresaDTO> atualizarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        try {
            EmpresaDTO empresaAtualizada = empresaService.atualizarEmpresa(empresaDTO);
            return ResponseEntity.ok(empresaAtualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para deletar uma empresa
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE')")
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        try {
            empresaService.deletarEmpresa(empresaDTO.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}