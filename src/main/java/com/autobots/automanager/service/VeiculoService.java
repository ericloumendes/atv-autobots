package com.autobots.automanager.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.DTO.VeiculoDTO;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.mapper.VeiculoMapper;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class VeiculoService {

    @Autowired
    private RepositorioVeiculo repositorio;

    @Autowired
    private RepositorioUsuario usuarioRepositorio;

    // Método para obter todos os veículos
    public List<VeiculoDTO> obterVeiculos() {
        List<Veiculo> veiculos = repositorio.findAll();
        return veiculos.stream()
                .map(veiculo -> VeiculoMapper.toDTO(veiculo))
                .collect(Collectors.toList());
    }

    // Método para obter um veículo específico pelo ID
    public VeiculoDTO obterVeiculo(Long id) {
        Veiculo veiculo = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com id: " + id));
        return VeiculoMapper.toDTO(veiculo);
    }

    // Método para cadastrar um novo veículo e associá-lo a um usuário
    public VeiculoDTO cadastrarVeiculo(VeiculoDTO dto, Long id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));

        Veiculo veiculo = VeiculoMapper.toEntity(dto);
        veiculo.setProprietario(usuario);  
        Veiculo veiculoCadastrado = repositorio.save(veiculo);
        
        usuario.getVeiculos().add(veiculoCadastrado);
        usuarioRepositorio.save(usuario);
        
        return VeiculoMapper.toDTO(veiculoCadastrado);
    }

    // Método para atualizar um veículo existente
    public VeiculoDTO atualizarVeiculo(VeiculoDTO dto) {
        Veiculo veiculo = repositorio.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com id: " + dto.getId()));

        if (dto.getTipo() != null) {
            veiculo.setTipo(dto.getTipo());
        }

        if (dto.getModelo() != null) {
            veiculo.setModelo(dto.getModelo());
        }

        if (dto.getPlaca() != null) {
            veiculo.setPlaca(dto.getPlaca());
        }

        Veiculo veiculoAtualizado = repositorio.save(veiculo);
        return VeiculoMapper.toDTO(veiculoAtualizado);
    }

    // Método para deletar um veículo
    public void deletarVeiculo(VeiculoDTO dto) {
        Veiculo veiculo = repositorio.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com id: " + dto.getId()));
        
        // Desassociar o veículo do usuário
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findById(veiculo.getProprietario().getId());
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getVeiculos().remove(veiculo);  // Removendo o veículo da coleção de veículos do usuário
            usuarioRepositorio.save(usuario);  // Salvando o usuário após a remoção
        }

        // Excluindo o veículo
        repositorio.delete(veiculo);
    }

}
