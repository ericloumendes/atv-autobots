package com.autobots.automanager.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.DTO.ServicoDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.mapper.ServicoMapper;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioServico;

@Service
public class ServicoService {

    @Autowired
    private RepositorioServico repositorio;

    @Autowired
    private RepositorioEmpresa empresaRepositorio;

    public List<ServicoDTO> obterServicos() {
        List<Servico> servicos = repositorio.findAll();
        return servicos.stream()
                .map(ServicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ServicoDTO obterServico(Long id) {
        Servico servico = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id: " + id));
        return ServicoMapper.toDTO(servico);
    }

    public ServicoDTO cadastrarServicoEmpresa(ServicoDTO dto, Long empresaId) {
        Empresa empresa = empresaRepositorio.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com id: " + empresaId));

        Servico servico = ServicoMapper.toEntity(dto);
        Servico servicoCadastrado = repositorio.save(servico);

        empresa.getServicos().add(servicoCadastrado);
        empresaRepositorio.save(empresa);

        return ServicoMapper.toDTO(servicoCadastrado);
    }

    public ServicoDTO atualizarServico(ServicoDTO dto) {
        Servico servico = repositorio.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id: " + dto.getId()));

        if (dto.getNome() != null) {
            servico.setNome(dto.getNome());
        }
        if (dto.getValor() > 0) {
            servico.setValor(dto.getValor());
        }
        if (dto.getDescricao() != null) {
            servico.setDescricao(dto.getDescricao());
        }

        Servico servicoAtualizado = repositorio.save(servico);
        return ServicoMapper.toDTO(servicoAtualizado);
    }

    public void deletarServico(ServicoDTO dto) {
        Servico servico = repositorio.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id: " + dto.getId()));

        Optional<Empresa> empresaOpt = empresaRepositorio.findByServicosId(dto.getId());
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            empresa.getServicos().remove(servico);
            empresaRepositorio.save(empresa);
        } else {
            throw new RuntimeException("Serviço não está associado a nenhuma empresa");
        }

        repositorio.delete(servico);
    }
}
