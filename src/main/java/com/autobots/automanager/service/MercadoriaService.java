package com.autobots.automanager.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.mapper.MercadoriaMapper;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class MercadoriaService {
	
	@Autowired
	RepositorioMercadoria repositorio;
	
	@Autowired
	private RepositorioUsuario usuarioRepositorio;
	
	@Autowired
	private RepositorioEmpresa empresaRepositorio;
	
	public List<MercadoriaDTO> obterMercadorias(){
		List<Mercadoria> mercadorias = repositorio.findAll();
		return mercadorias.stream()
				.map(mercadoria -> MercadoriaMapper.toDTO(mercadoria))
				.collect(Collectors.toList());
	}
	
	public MercadoriaDTO obterMercadoria(Long id){
		Mercadoria mercadoria = repositorio.findById(id).get();
		return MercadoriaMapper.toDTO(mercadoria);
	}
	
	public MercadoriaDTO cadastrarMercadoriaUsuario(MercadoriaDTO dto, Long usuarioId) {
		Usuario usuario = usuarioRepositorio.findById(usuarioId)
		        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));
		    
		Mercadoria mercadoria = MercadoriaMapper.toEntity(dto);
		Mercadoria mercadoriaCadastrada = repositorio.save(mercadoria);
	    
	    usuario.getMercadorias().add(mercadoriaCadastrada);
	    usuarioRepositorio.save(usuario);
	    
	    return MercadoriaMapper.toDTO(mercadoriaCadastrada);
	}
	
	public MercadoriaDTO cadastrarMercadoriaEmpresa(MercadoriaDTO dto, Long empresaId) {
		Empresa empresa = empresaRepositorio.findById(empresaId)
		        .orElseThrow(() -> new RuntimeException("Empresa não encontrado com id: " + empresaId));
		    
		Mercadoria mercadoria = MercadoriaMapper.toEntity(dto);
		Mercadoria mercadoriaCadastrada = repositorio.save(mercadoria);
	    
		empresa.getMercadorias().add(mercadoriaCadastrada);
		empresaRepositorio.save(empresa);
	    
	    return MercadoriaMapper.toDTO(mercadoriaCadastrada);
	}
	
	public MercadoriaDTO atualizarMercadoria(MercadoriaDTO dto) {
		Mercadoria mercadoria = repositorio.findById(dto.getId())
				.orElseThrow(() -> new RuntimeException("mercadoria não encontrada com id: " + dto.getId()));
		
		if(dto.getValidade() != null) {
			mercadoria.setValidade(dto.getValidade());
		}
		
		if(dto.getFabricacao() != null) {
			mercadoria.setFabricacao(dto.getFabricacao());
		}
		
		if(dto.getCadastro() != null) {
			mercadoria.setValidade(dto.getCadastro());
		}
		
		if(dto.getNome() != null) {
			mercadoria.setNome(dto.getNome());
		}
		
		if(dto.getQuantidade() > 0) {
			mercadoria.setQuantidade(dto.getQuantidade());
		}
		
		if(dto.getValor()  > 0) {
			mercadoria.setValor(dto.getValor());
		}
		
		if(dto.getDescricao() != null) {
			mercadoria.setDescricao(dto.getDescricao());
		}
		
		Mercadoria mercadoriaAtualizada = repositorio.save(mercadoria);
		return MercadoriaMapper.toDTO(mercadoriaAtualizada);
	}
	
	public void deletarMercadoria(MercadoriaDTO dto) {
		Mercadoria mercadoria = repositorio.findById(dto.getId())
	        .orElseThrow(() -> new RuntimeException("Mercadoria não encontrado com id: " + dto.getId()));

	    Optional<Usuario> usuarioOpt = usuarioRepositorio.findByMercadoriasId(dto.getId());
	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        usuario.getMercadorias().remove(mercadoria);
	        usuarioRepositorio.save(usuario);
	    }

	    Optional<Empresa> empresaOpt = empresaRepositorio.findByMercadoriasId(dto.getId());
	    if (empresaOpt.isPresent()) {
	        Empresa empresa = empresaOpt.get();
	        empresa.getMercadorias().remove(mercadoria);
	        empresaRepositorio.save(empresa);
	    }

	    if (usuarioOpt.isEmpty() && empresaOpt.isEmpty()) {
	        throw new RuntimeException("Mercadoria não está associado a nenhum usuário ou empresa");
	    }

	    repositorio.delete(mercadoria);
	}
}
