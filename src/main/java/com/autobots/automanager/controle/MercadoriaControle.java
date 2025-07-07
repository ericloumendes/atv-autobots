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

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.modelo.AdicionadorLinkMercadoria;
import com.autobots.automanager.service.MercadoriaService;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {
	
	@Autowired
	private MercadoriaService service;
	
	@Autowired
	private AdicionadorLinkMercadoria adicionador;
	
	@GetMapping("/")
	public ResponseEntity<List<MercadoriaDTO>> obterMercadorias(){
		try {
			List<MercadoriaDTO> mercadorias = service.obterMercadorias();
			adicionador.adicionadorLink(mercadorias);
			if(mercadorias != null) {
				return ResponseEntity.status(HttpStatus.FOUND).body(mercadorias);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}	catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} 	catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MercadoriaDTO> obterMercadoria(@PathVariable Long id){
		try {
			MercadoriaDTO mercadoria = service.obterMercadoria(id);
			adicionador.adicionadorLink(mercadoria);
			if(mercadoria != null) {
				return ResponseEntity.status(HttpStatus.FOUND).body(mercadoria);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}	catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} 	catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/cadastrar/usuario/{usuarioId}")
	public ResponseEntity<MercadoriaDTO> cadastrarMercadoriaUsuario(@RequestBody MercadoriaDTO mercadoria, @PathVariable Long usuarioId){
		try {
			MercadoriaDTO mercadoriaCadastrada = service.cadastrarMercadoriaUsuario(mercadoria, usuarioId);
			if(mercadoriaCadastrada != null) {
				return ResponseEntity.ok(mercadoriaCadastrada);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/cadastrar/empresa/{empresaId}")
	public ResponseEntity<MercadoriaDTO> cadastrarMercadoriaEmpresa(@RequestBody MercadoriaDTO mercadoria, @PathVariable Long empresaId){
		try {
			MercadoriaDTO mercadoriaCadastrada = service.cadastrarMercadoriaEmpresa(mercadoria, empresaId);
			if(mercadoriaCadastrada != null) {
				return ResponseEntity.ok(mercadoriaCadastrada);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<MercadoriaDTO> atualizarMercadoria(@RequestBody MercadoriaDTO mercadoria){
		try {
			MercadoriaDTO mercadoriaAtualizado = service.atualizarMercadoria(mercadoria);
			if(mercadoriaAtualizado != null) {
				return ResponseEntity.ok(mercadoriaAtualizado);
			}
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/deletar")
	public ResponseEntity<Void> deletarMercadoria(@RequestBody MercadoriaDTO mercadoria) {
	    try {
	        service.deletarMercadoria(mercadoria);
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
