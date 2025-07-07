package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.DTO.VeiculoDTO;
import com.autobots.automanager.modelo.AdicionadorLinkVeiculo;
import com.autobots.automanager.service.VeiculoService;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {

    @Autowired
    private VeiculoService service;
    
    @Autowired
	private AdicionadorLinkVeiculo adicionadorLink;

    @GetMapping("/")
    public ResponseEntity<List<VeiculoDTO>> obterVeiculos() {
        try {
            List<VeiculoDTO> veiculos = service.obterVeiculos();
            adicionadorLink.adicionadorLink(veiculos);
            if (veiculos != null && !veiculos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(veiculos);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> obterVeiculo(@PathVariable Long id) {
        try {
            VeiculoDTO veiculo = service.obterVeiculo(id);
            adicionadorLink.adicionadorLink(veiculo);
            if (veiculo != null) {
                return ResponseEntity.status(HttpStatus.FOUND).body(veiculo);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarVeiculo(@RequestBody VeiculoDTO veiculo) {
        try {
            service.deletarVeiculo(veiculo);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
