package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.DTO.VendaDTO;
import com.autobots.automanager.service.VendaService;

@RestController
@RequestMapping("/venda")
public class VendaControle {

    @Autowired
    private VendaService service;
    
    @GetMapping("/")
    public ResponseEntity<List<VendaDTO>> obterVendas() {
        try {
            List<VendaDTO> vendas = service.obterVendas();
            if (vendas != null && !vendas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(vendas);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> obterVenda(@PathVariable Long id) {
        try {
            VendaDTO venda = service.obterVenda(id);
            if (venda != null) {
                return ResponseEntity.status(HttpStatus.FOUND).body(venda);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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
