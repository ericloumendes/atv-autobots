package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.DTO.ServicoDTO;
import com.autobots.automanager.modelo.AdicionadorLinkServico;
import com.autobots.automanager.service.ServicoService;

@RestController
@RequestMapping("/servico")
public class ServicoControle {

    @Autowired
    private ServicoService service;
    
    @Autowired
	private AdicionadorLinkServico adicionadorLink;

    @GetMapping("/")
    public ResponseEntity<List<ServicoDTO>> obterServicos() {
        try {
            List<ServicoDTO> servicos = service.obterServicos();
            adicionadorLink.adicionadorLink(servicos);
            if (servicos != null && !servicos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(servicos);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> obterServico(@PathVariable Long id) {
        try {
            ServicoDTO servico = service.obterServico(id);
            adicionadorLink.adicionadorLink(servico);
            if (servico != null) {
                return ResponseEntity.status(HttpStatus.FOUND).body(servico);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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
