package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.DTO.CredencialDTO;
import com.autobots.automanager.DTO.CredencialUsuarioSenhaDTO;
import com.autobots.automanager.modelo.AdicionadorLinkCredencial;
import com.autobots.automanager.DTO.CredencialCodigoBarraDTO;
import com.autobots.automanager.service.CredencialService;

@RestController
@RequestMapping("/credencial")
public class CredencialControle {

    @Autowired
    private CredencialService service;
    
    @Autowired
    private AdicionadorLinkCredencial adicionadorLink;

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

    @GetMapping("/{id}")
    public ResponseEntity<CredencialDTO> obterCredencial(@PathVariable Long id) {
        try {
            CredencialDTO credencial = service.obterCredencial(id);
            adicionadorLink.adicionadorLink(credencial);
            if (credencial != null) {
                return ResponseEntity.status(HttpStatus.FOUND).body(credencial);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cadastrar/usuario")
    public ResponseEntity<CredencialUsuarioSenhaDTO> cadastrarCredencialUsuario(@RequestBody CredencialUsuarioSenhaDTO credencialDTO) {
        try {
            CredencialUsuarioSenhaDTO credencialCadastrada = service.cadastrarCredencialUsuario(credencialDTO);
            return ResponseEntity.ok(credencialCadastrada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cadastrar/codigo-barra")
    public ResponseEntity<CredencialCodigoBarraDTO> cadastrarCredencialCodigoBarra(@RequestBody CredencialCodigoBarraDTO credencialDTO) {
        try {
            CredencialCodigoBarraDTO credencialCadastrada = service.cadastrarCredencialCodigoBarra(credencialDTO);
            return ResponseEntity.ok(credencialCadastrada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<CredencialDTO> atualizarCredencial(@RequestBody CredencialDTO credencialDTO) {
        try {
            CredencialDTO credencialAtualizada = service.atualizarCredencial(credencialDTO);
            return ResponseEntity.ok(credencialAtualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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
