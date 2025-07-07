package com.autobots.automanager.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.DTO.EmailDTO;
import com.autobots.automanager.modelo.AdicionadorLinkEmail;
import com.autobots.automanager.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailControle {

    @Autowired
    private EmailService service;
    
    @Autowired
    AdicionadorLinkEmail adicionador;

    @GetMapping("/")
    public ResponseEntity<List<EmailDTO>> obterEmails() {
        try {
            List<EmailDTO> emails = service.obterEmails();
            adicionador.adicionadorLink(emails);
            if (emails != null && !emails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FOUND).body(emails);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailDTO> obterEmail(@PathVariable Long id) {
        try {
            EmailDTO email = service.obterEmail(id);
            adicionador.adicionadorLink(email);
            if (email != null) {
                return ResponseEntity.status(HttpStatus.FOUND).body(email);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<EmailDTO> cadastrarEmail(@RequestBody EmailDTO email, @PathVariable Long usuarioId) {
        try {
            EmailDTO emailCadastrado = service.cadastrarEmail(email, usuarioId);
            if (emailCadastrado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(emailCadastrado);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<EmailDTO> atualizarEmail(@RequestBody EmailDTO email) {
        try {
            EmailDTO emailAtualizado = service.atualizarEmail(email);
            if (emailAtualizado != null) {
                return ResponseEntity.ok(emailAtualizado);
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarEmail(@RequestBody EmailDTO email) {
        try {
            service.deletarEmail(email);
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
