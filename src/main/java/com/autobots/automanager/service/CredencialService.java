package com.autobots.automanager.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.DTO.CredencialDTO;
import com.autobots.automanager.DTO.CredencialUsuarioSenhaDTO;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.mapper.CredencialMapper;
import com.autobots.automanager.repositorios.RepositorioCredencial;
import com.autobots.automanager.repositorios.RepositorioCredencialUsuarioSenha;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class CredencialService {

    @Autowired
    private RepositorioCredencial repositorioCredencial;

    @Autowired
    private RepositorioCredencialUsuarioSenha repositorioCredencialUsuarioSenha;

    
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public List<CredencialDTO> obterCredenciais() {
        List<Credencial> credenciais = repositorioCredencial.findAll();
        return credenciais.stream().map(CredencialMapper::toDTO).collect(Collectors.toList());
    }

    public CredencialDTO obterCredencial(Long id) {
        Credencial credencial = repositorioCredencial.findById(id)
                .orElseThrow(() -> new RuntimeException("Credencial não encontrada com id: " + id));
        return CredencialMapper.toDTO(credencial);
    }

    public CredencialUsuarioSenhaDTO cadastrarCredencialUsuario(CredencialUsuarioSenhaDTO dto) {
        CredencialUsuarioSenha credencial = new CredencialUsuarioSenha();
        credencial.setCriacao(new Date());
        credencial.setNomeUsuario(dto.getNomeUsuario());
        credencial.setSenha(dto.getSenha());

        CredencialUsuarioSenha credencialSalva = repositorioCredencialUsuarioSenha.save(credencial);
        return CredencialMapper.toCredencialUsuarioSenhaDTO(credencialSalva);
    }


    public CredencialDTO atualizarCredencial(CredencialDTO dto) {
        Credencial credencial = repositorioCredencial.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Credencial não encontrada com id: " + dto.getId()));

        credencial.setUltimoAcesso(new Date());
        credencial.setInativo(dto.isInativo());

        Credencial credencialAtualizada = repositorioCredencial.save(credencial);
        return CredencialMapper.toDTO(credencialAtualizada);
    }

    public void deletarCredencial(CredencialDTO dto) {
        Credencial credencial = repositorioCredencial.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Credencial não encontrada com id: " + dto.getId()));
        
        Optional<Usuario> usuarioOpt = repositorioUsuario.findByDocumentosId(dto.getId());
        if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        usuario.getCredenciais().remove(credencial);
	        repositorioUsuario.save(usuario);
	    }
        repositorioCredencial.delete(credencial);
    }
}
