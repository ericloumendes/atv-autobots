package com.autobots.automanager.service;

import com.autobots.automanager.DTO.EnderecoDTO;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.mapper.EnderecoMapper;
import com.autobots.automanager.repositorios.RepositorioEndereco;
import com.autobots.automanager.repositorios.RepositorioUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private RepositorioEndereco repositorioEndereco;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    // Método para obter todos os endereços
    public List<EnderecoDTO> obterEnderecos() {
        List<Endereco> enderecos = repositorioEndereco.findAll();
        return enderecos.stream().map(EnderecoMapper::toDTO).collect(Collectors.toList());
    }

    // Método para obter um único endereço por id
    public EnderecoDTO obterEndereco(Long id) {
        Endereco endereco = repositorioEndereco.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com id: " + id));
        return EnderecoMapper.toDTO(endereco);
    }

    // Método para salvar um novo endereço
    public EnderecoDTO cadastrarEndereco(EnderecoDTO dto, Long id) {
    	Usuario usuario = repositorioUsuario.findById(id)
    			.orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    	
        Endereco endereco = EnderecoMapper.toEntity(dto);
        Endereco enderecoSalvo = repositorioEndereco.save(endereco);
        if(usuario != null) {
        	usuario.setEndereco(enderecoSalvo);
        	repositorioUsuario.save(usuario);
        }
        return EnderecoMapper.toDTO(enderecoSalvo);
    }

    // Método para atualizar um endereço existente
    public EnderecoDTO atualizarEndereco(EnderecoDTO dto) {
        Endereco endereco = repositorioEndereco.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com id: " + dto.getId()));

        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setCodigoPostal(dto.getCodigoPostal());
        endereco.setInformacoesAdicionais(dto.getInformacoesAdicionais());

        Endereco enderecoAtualizado = repositorioEndereco.save(endereco);
        return EnderecoMapper.toDTO(enderecoAtualizado);
    }

    // Método para deletar um endereço
    public void deletarEndereco(Long id) {
        Endereco endereco = repositorioEndereco.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com id: " + id));
        
        Optional<Usuario> usuarioOpt = repositorioUsuario.findByEnderecoId(id);
	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        usuario.setEndereco(null);
	        repositorioUsuario.save(usuario);
	    }

        repositorioEndereco.delete(endereco);
    }
}
