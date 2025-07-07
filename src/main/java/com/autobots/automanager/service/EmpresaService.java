package com.autobots.automanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.DTO.EmpresaDTO;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.mapper.EmpresaMapper;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@Service
public class EmpresaService {
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    // Método para obter todos as empresas
    public List<EmpresaDTO> obterEmpresas() {
        List<Empresa> empresas = repositorioEmpresa.findAll();
        return empresas.stream().map(EmpresaMapper::toDTO).collect(Collectors.toList());
    }

    // Método para obter uma única empresa por id
    public EmpresaDTO obterEmpresa(Long id) {
        Empresa empresa = repositorioEmpresa.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com id: " + id));
        return EmpresaMapper.toDTO(empresa);
    }

    // Método para salvar uma nova empresa
    public EmpresaDTO cadastrarEmpresa(EmpresaDTO dto) {
        Empresa empresa = EmpresaMapper.toEntity(dto);
        Empresa empresaSalva = repositorioEmpresa.save(empresa);

        return EmpresaMapper.toDTO(empresaSalva);
    }

    // Método para atualizar uma empresa existente
    public EmpresaDTO atualizarEmpresa(EmpresaDTO dto) {
        Empresa empresa = repositorioEmpresa.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com id: " + dto.getId()));

        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setRazaoSocial(dto.getRazaoSocial());

        Empresa empresaAtualizada = repositorioEmpresa.save(empresa);
        return EmpresaMapper.toDTO(empresaAtualizada);
    }

    // Método para deletar uma empresa
    public void deletarEmpresa(Long id) {
        Empresa empresa = repositorioEmpresa.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrado com id: " + id));

        repositorioEmpresa.delete(empresa);
    }
}
