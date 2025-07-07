package com.autobots.sistema.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.sistema.DTO.CredencialDTO;
import com.autobots.sistema.DTO.CredencialUsuarioSenhaDTO;
import com.autobots.sistema.entidades.Credencial;
import com.autobots.sistema.entidades.CredencialUsuarioSenha;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.mapper.CredencialMapper;
import com.autobots.sistema.repositorios.RepositorioCredencial;
import com.autobots.sistema.repositorios.RepositorioCredencialUsuarioSenha;
import com.autobots.sistema.repositorios.RepositorioUsuario;

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
