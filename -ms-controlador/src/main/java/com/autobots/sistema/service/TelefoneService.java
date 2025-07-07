package com.autobots.sistema.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.sistema.DTO.TelefoneDTO;
import com.autobots.sistema.entidades.Empresa;
import com.autobots.sistema.entidades.Telefone;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.mapper.TelefoneMapper;
import com.autobots.sistema.repositorios.RepositorioEmpresa;
import com.autobots.sistema.repositorios.RepositorioTelefone;
import com.autobots.sistema.repositorios.RepositorioUsuario;

@Service
public class TelefoneService {
	
	@Autowired
	private RepositorioTelefone repositorio;
	
	@Autowired
	private RepositorioUsuario usuarioRepositorio;
	
	@Autowired
	private RepositorioEmpresa empresaRepositorio;
	
	public List<TelefoneDTO> obterTelefones(){
		List<Telefone> telefones = repositorio.findAll();
		return telefones.stream()
				.map(telefone -> TelefoneMapper.toDTO(telefone))
				.collect(Collectors.toList());
	}
	
	public TelefoneDTO obterTelefone(Long id) {
		Telefone telefone = repositorio.findById(id).get();
		return TelefoneMapper.toDTO(telefone);
	}
	
	public TelefoneDTO cadastrarTelefone(TelefoneDTO dto, Long usuarioId) {
	    Usuario usuario = usuarioRepositorio.findById(usuarioId)
	        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));
	    
	    Telefone telefone = TelefoneMapper.toEntity(dto);
	    Telefone telefoneCadastrado = repositorio.save(telefone);
	    
	    usuario.getTelefones().add(telefoneCadastrado);
	    usuarioRepositorio.save(usuario);
	    
	    return TelefoneMapper.toDTO(telefoneCadastrado);
	}
	
	public TelefoneDTO atualizarTelefone(TelefoneDTO dto) {
		Telefone telefone = repositorio.findById(dto.getId())
				.orElseThrow(() -> new RuntimeException("Telefone não encontrado com id: " + dto.getId()));
		
		if(dto.getDdd() != null) {
			telefone.setDdd(dto.getDdd());
		}
		
		if(dto.getNumero() != null) {
			telefone.setNumero(dto.getNumero());
		}
		
		Telefone telefoneAtualizado = repositorio.save(telefone);
		return TelefoneMapper.toDTO(telefoneAtualizado);
	}
	
	public void deletarTelefone(TelefoneDTO dto) {
	    Telefone telefone = repositorio.findById(dto.getId())
	        .orElseThrow(() -> new RuntimeException("Telefone não encontrado com id: " + dto.getId()));

	    Optional<Usuario> usuarioOpt = usuarioRepositorio.findByTelefonesId(dto.getId());
	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        usuario.getTelefones().remove(telefone);
	        usuarioRepositorio.save(usuario);
	    }

	    Optional<Empresa> empresaOpt = empresaRepositorio.findByTelefonesId(dto.getId());
	    if (empresaOpt.isPresent()) {
	        Empresa empresa = empresaOpt.get();
	        empresa.getTelefones().remove(telefone);
	        empresaRepositorio.save(empresa);
	    }

	    if (usuarioOpt.isEmpty() && empresaOpt.isEmpty()) {
	        throw new RuntimeException("Telefone não está associado a nenhum usuário ou empresa");
	    }

	    repositorio.delete(telefone);
	}

}
