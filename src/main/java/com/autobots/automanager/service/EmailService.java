package com.autobots.automanager.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.DTO.EmailDTO;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.mapper.EmailMapper;
import com.autobots.automanager.repositorios.RepositorioEmail;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class EmailService {
	
	@Autowired
	private RepositorioEmail repositorio;
	
	@Autowired
	private RepositorioUsuario usuarioRepositorio;
	
	public List<EmailDTO> obterEmails(){
		List<Email> emails = repositorio.findAll();
		return emails.stream()
				.map(email -> EmailMapper.toDTO(email))
				.collect(Collectors.toList());
	}
	
	public EmailDTO obterEmail(Long id) {
		Email email = repositorio.findById(id).get();
		return EmailMapper.toDTO(email);
	}
	
	public EmailDTO cadastrarEmail(EmailDTO dto, Long id) {
		Usuario usuario = usuarioRepositorio.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + dto.getId()));
		Email email = EmailMapper.toEntity(dto);
		Email emailCadastrado = repositorio.save(email);
		usuario.getEmails().add(emailCadastrado);
		usuarioRepositorio.save(usuario);
		return EmailMapper.toDTO(emailCadastrado);
	}
	
	public EmailDTO atualizarEmail(EmailDTO dto) {
		Email email = repositorio.findById(dto.getId())
				.orElseThrow(() -> new RuntimeException("Email não encontrado com id: " + dto.getId()));
		
		if(dto.getEndereco() != null) {
			email.setEndereco(dto.getEndereco());
		}
		
		Email emailAtualizado = repositorio.save(email);
		return EmailMapper.toDTO(emailAtualizado);
	}
	
	public void deletarEmail(EmailDTO dto) {
		Email email = repositorio.findById(dto.getId())
				.orElseThrow(() -> new RuntimeException("Email não encontrado com id: " + dto.getId()));
		
		Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmailsId(dto.getId());
	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        usuario.getEmails().remove(email);
	        usuarioRepositorio.save(usuario);
	    }
	    
	    if (usuarioOpt.isEmpty()) {
	        throw new RuntimeException("Email não está associado a nenhum usuário");
	    }
	    
	    repositorio.delete(email);
	}
}
