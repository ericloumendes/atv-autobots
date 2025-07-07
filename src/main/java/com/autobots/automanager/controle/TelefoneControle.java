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

import com.autobots.automanager.DTO.TelefoneDTO;
import com.autobots.automanager.modelo.AdicionadorLinkTelefone;
import com.autobots.automanager.service.TelefoneService;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	
	@Autowired
	private TelefoneService service;
	
	@Autowired
	private AdicionadorLinkTelefone adicionadorLink;
	
	@GetMapping("/")
	public ResponseEntity<List<TelefoneDTO>> obterTelefones(){
		try {
			List<TelefoneDTO> telefones = service.obterTelefones();
			adicionadorLink.adicionadorLink(telefones);
			if(telefones != null) {
				return ResponseEntity.status(HttpStatus.FOUND).body(telefones);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}	catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} 	catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TelefoneDTO> obterTelefone(@PathVariable Long id){
		try {
			TelefoneDTO telefone = service.obterTelefone(id);
			adicionadorLink.adicionadorLink(telefone);
			if(telefone != null) {
				return ResponseEntity.status(HttpStatus.FOUND).body(telefone);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}	catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} 	catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/cadastrar/{usuarioId}")
	public ResponseEntity<TelefoneDTO> cadastrarCliente(@RequestBody TelefoneDTO telefone, @PathVariable Long usuarioId){
		try {
			TelefoneDTO telefoneAtualizado = service.cadastrarTelefone(telefone, usuarioId);
			if(telefoneAtualizado != null) {
				return ResponseEntity.ok(telefoneAtualizado);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<TelefoneDTO> atualizarTelefone(@RequestBody TelefoneDTO telefone){
		try {
			TelefoneDTO telefoneAtualizado = service.atualizarTelefone(telefone);
			if(telefoneAtualizado != null) {
				return ResponseEntity.ok(telefoneAtualizado);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/deletar")
	public ResponseEntity<Void> deletarTelefone(@RequestBody TelefoneDTO telefone) {
	    try {
	        service.deletarTelefone(telefone);
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
