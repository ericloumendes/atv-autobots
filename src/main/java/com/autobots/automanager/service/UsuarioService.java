package com.autobots.automanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.automanager.DTO.DocumentoDTO;
import com.autobots.automanager.DTO.UsuarioDTO;
import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.mapper.DocumentoMapper;
import com.autobots.automanager.mapper.EnderecoMapper;
import com.autobots.automanager.mapper.UsuarioMapper;
import com.autobots.automanager.modelo.AdicionadorLinkCredencial;
import com.autobots.automanager.modelo.AdicionadorLinkDocumento;
import com.autobots.automanager.modelo.AdicionadorLinkEmail;
import com.autobots.automanager.modelo.AdicionadorLinkEndereco;
import com.autobots.automanager.modelo.AdicionadorLinkMercadoria;
import com.autobots.automanager.modelo.AdicionadorLinkTelefone;
import com.autobots.automanager.modelo.AdicionadorLinkUsuario;
import com.autobots.automanager.modelo.AdicionadorLinkVeiculo;
import com.autobots.automanager.modelo.AdicionadorLinkVenda;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class UsuarioService {
	
	@Autowired
	private RepositorioUsuario repositorio;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLink;
	
	@Autowired
	private AdicionadorLinkEndereco adicionadorLinkEndereco;
	
	@Autowired
	private AdicionadorLinkDocumento adicionadorLinkDocumento;
	
	@Autowired
	private AdicionadorLinkEmail adicionadorLinkEmail;
	
	@Autowired
	private AdicionadorLinkTelefone adicionadorLinkTelefone;
	
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLinkMercadoria;
	
	@Autowired
	private AdicionadorLinkVenda adicionadorLinkVenda;
	
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLinkVeiculo;
	
	@Autowired
	private AdicionadorLinkCredencial adicionadorLinkCredencial;
	
	public List<UsuarioDTO> obterClientes() {
		List<Usuario> usuarios = repositorio.findAll();
		List<UsuarioDTO> usuariosDTO = usuarios.stream()
				.map(usuario -> UsuarioMapper.toDTO(usuario))
				.collect(Collectors.toList());
		
		for (UsuarioDTO usuario : usuariosDTO) {
			if(usuario.getEndereco() != null) {
				adicionadorLinkEndereco.adicionadorLink(usuario.getEndereco());
			}
		    usuario.getDocumentos().forEach(adicionadorLinkDocumento::adicionadorLink);
		    usuario.getEmails().forEach(adicionadorLinkEmail::adicionadorLink);
		    usuario.getCredenciais().forEach(adicionadorLinkCredencial::adicionadorLink);
		    usuario.getTelefones().forEach(adicionadorLinkTelefone::adicionadorLink);
		    usuario.getMercadorias().forEach(adicionadorLinkMercadoria::adicionadorLink);
		    usuario.getVendas().forEach(adicionadorLinkVenda::adicionadorLink);
		    usuario.getVeiculos().forEach(adicionadorLinkVeiculo::adicionadorLink);
		}
		adicionadorLink.adicionadorLink(usuariosDTO);
		return usuariosDTO;
	}
	
	public UsuarioDTO obterCliente(Long id) {
		Usuario usuario =  repositorio.findById(id).get();
		UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuario);
		usuarioDTO.getDocumentos().forEach(adicionadorLinkDocumento::adicionadorLink);
		usuarioDTO.getEmails().forEach(adicionadorLinkEmail::adicionadorLink);
		usuarioDTO.getCredenciais().forEach(adicionadorLinkCredencial::adicionadorLink);
		usuarioDTO.getTelefones().forEach(adicionadorLinkTelefone::adicionadorLink);
		usuarioDTO.getMercadorias().forEach(adicionadorLinkMercadoria::adicionadorLink);
		usuarioDTO.getVendas().forEach(adicionadorLinkVenda::adicionadorLink);
		usuarioDTO.getVeiculos().forEach(adicionadorLinkVeiculo::adicionadorLink);
		adicionadorLink.adicionadorLink(usuarioDTO);
		return usuarioDTO;
	}
	
	public UsuarioDTO cadastrarCliente(UsuarioDTO dto) {
		Usuario usuario = UsuarioMapper.toEntity(dto); 
		Usuario usuarioCadastrado = repositorio.save(usuario);
		return UsuarioMapper.toDTO(usuarioCadastrado);
	}
	
	public UsuarioDTO atualizarCliente(UsuarioDTO dto) {
		Usuario usuario = repositorio.findById(dto.getId())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + dto.getId()));
		
		if(dto.getNome() != null) {
			usuario.setNome(dto.getNome());
		}
		
		if(dto.getNomeSocial() != null) {
			usuario.setNomeSocial(dto.getNomeSocial());
		}
		
		if(dto.getPerfis() != null) {
			usuario.setPerfis(dto.getPerfis());
		}
		
		repositorio.save(usuario);
		return UsuarioMapper.toDTO(usuario);
	}
	
	@Transactional
	public void deletarCliente(UsuarioDTO dto) {
	    // Encontrar o usuário pelo ID
	    Usuario usuario = repositorio.findById(dto.getId())
	        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + dto.getId()));

	    // Log para verificação
	    System.out.println("Deletando usuário com id: " + usuario.getId());

	    // Limpar os relacionamentos
	    if (usuario.getVendas() != null) {
	        usuario.getVendas().clear(); // Limpar vendas
	    }

	    if (usuario.getVeiculos() != null) {
	        usuario.getVeiculos().clear(); // Limpar veículos
	    }

	    if (usuario.getMercadorias() != null) {
	        usuario.getMercadorias().clear(); // Limpar mercadorias
	    }

	    if (usuario.getCredenciais() != null) {
	        usuario.getCredenciais().clear(); // Limpar credenciais
	    }

	    if (usuario.getDocumentos() != null) {
	        usuario.getDocumentos().clear(); // Limpar documentos
	    }

	    if (usuario.getEmails() != null) {
	        usuario.getEmails().clear(); // Limpar emails
	    }

	    if (usuario.getTelefones() != null) {
	        usuario.getTelefones().clear(); // Limpar telefones
	    }

	    if (usuario.getEndereco() != null) {
	        usuario.setEndereco(null); 
	    }

	    repositorio.delete(usuario);
	}

}
