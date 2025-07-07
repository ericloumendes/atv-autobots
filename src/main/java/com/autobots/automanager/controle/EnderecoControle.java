package com.autobots.automanager.controle;

import com.autobots.automanager.DTO.EnderecoDTO;
import com.autobots.automanager.modelo.AdicionadorLinkEndereco;
import com.autobots.automanager.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {

    @Autowired
    private EnderecoService enderecoService;
    
    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    // Endpoint para obter todos os endereços
    @GetMapping("/")
    public ResponseEntity<List<EnderecoDTO>> obterEnderecos() {
        try {
            List<EnderecoDTO> enderecos = enderecoService.obterEnderecos();
            adicionadorLink.adicionadorLink(enderecos);
            if (enderecos != null && !enderecos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(enderecos);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para obter um único endereço por id
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> obterEndereco(@PathVariable Long id) {
        try {
            EnderecoDTO endereco = enderecoService.obterEndereco(id);
            adicionadorLink.adicionadorLink(endereco);
            if (endereco != null) {
                return ResponseEntity.status(HttpStatus.FOUND).body(endereco);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para cadastrar um novo endereço
    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(@RequestBody EnderecoDTO enderecoDTO, @PathVariable Long usuarioId) {
        try {
            EnderecoDTO enderecoCadastrado = enderecoService.cadastrarEndereco(enderecoDTO, usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(enderecoCadastrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para atualizar um endereço
    @PutMapping("/atualizar")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        try {
            EnderecoDTO enderecoAtualizado = enderecoService.atualizarEndereco(enderecoDTO);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para deletar um endereço
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
