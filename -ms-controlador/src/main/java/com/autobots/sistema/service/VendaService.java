package com.autobots.sistema.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autobots.sistema.DTO.VendaDTO;
import com.autobots.sistema.entidades.Empresa;
import com.autobots.sistema.entidades.Mercadoria;
import com.autobots.sistema.entidades.Servico;
import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.entidades.Veiculo;
import com.autobots.sistema.entidades.Venda;
import com.autobots.sistema.mapper.VendaMapper;
import com.autobots.sistema.modelo.AdicionadorLinkMercadoria;
import com.autobots.sistema.modelo.AdicionadorLinkServico;
import com.autobots.sistema.modelo.AdicionadorLinkVenda;
import com.autobots.sistema.repositorios.RepositorioEmpresa;
import com.autobots.sistema.repositorios.RepositorioMercadoria;
import com.autobots.sistema.repositorios.RepositorioServico;
import com.autobots.sistema.repositorios.RepositorioUsuario;
import com.autobots.sistema.repositorios.RepositorioVeiculo;
import com.autobots.sistema.repositorios.RepositorioVenda;

@Service
public class VendaService {

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private RepositorioServico repositorioServico;

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;
    
    @Autowired
    private AdicionadorLinkVenda adicionadorLink;
    
    @Autowired
    private AdicionadorLinkServico adicionadorLinkServico;
    
    @Autowired
    private AdicionadorLinkMercadoria adicionadorLinkMercadoria;

    public List<VendaDTO> obterVendas() {
        List<Venda> vendas = repositorioVenda.findAll();
        List<VendaDTO> vendasDTO = vendas.stream().map(VendaMapper::toDTO).collect(Collectors.toList());
        for(VendaDTO dto : vendasDTO) {
        	dto.getServicos().forEach(adicionadorLinkServico::adicionadorLink);
        	dto.getMercadorias().forEach(adicionadorLinkMercadoria::adicionadorLink);
        }
        adicionadorLink.adicionadorLink(vendasDTO);
        return vendasDTO;
    }

    public VendaDTO obterVenda(Long id) {
        Venda venda = repositorioVenda.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada com id: " + id));
        VendaDTO vendasDTO = VendaMapper.toDTO(venda);
        vendasDTO.getServicos().forEach(adicionadorLinkServico::adicionadorLink);
        vendasDTO.getMercadorias().forEach(adicionadorLinkMercadoria::adicionadorLink);
        adicionadorLink.adicionadorLink(vendasDTO);
        return vendasDTO;
    }

    public VendaDTO cadastrarVendaUsuario(VendaDTO dto, Long usuarioId) {

        Venda venda = VendaMapper.toEntity(dto);
        venda.setCadastro(new Date());

        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));
        venda.setCliente(usuario);

        if (dto.getFuncionarioId() != null) {
            Usuario funcionario = repositorioUsuario.findById(dto.getFuncionarioId())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
            venda.setFuncionario(funcionario);
        }

        if (dto.getVeiculoId() != null) {
            Veiculo veiculo = repositorioVeiculo.findById(dto.getVeiculoId())
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
            venda.setVeiculo(veiculo);
        }

        venda.getMercadorias().clear();
        if (dto.getMercadorias() != null) {
            for (var mercadoriaDTO : dto.getMercadorias()) {
                Mercadoria mercadoria = repositorioMercadoria.findById(mercadoriaDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Mercadoria não encontrada"));
                venda.getMercadorias().add(mercadoria);
            }
        }

        venda.getServicos().clear();
        if (dto.getServicos() != null) {
            for (var servicoDTO : dto.getServicos()) {
                Servico servico = repositorioServico.findById(servicoDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
                venda.getServicos().add(servico);
            }
        }

        Venda vendaSalva = repositorioVenda.save(venda);
        usuario.getVendas().add(vendaSalva);
        repositorioUsuario.save(usuario);
        return VendaMapper.toDTO(vendaSalva);
    }

    public VendaDTO cadastrarVendaEmpresa(VendaDTO dto, Long empresaId) {
        Venda venda = VendaMapper.toEntity(dto);
        venda.setCadastro(new Date());

        Empresa empresa = repositorioEmpresa.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com id: " + empresaId));

        // Define o relacionamento da venda com a empresa a partir do ID da URL
        venda.setEmpresa(empresa);

        if (dto.getFuncionarioId() != null) {
            Usuario funcionario = repositorioUsuario.findById(dto.getFuncionarioId())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
            venda.setFuncionario(funcionario);
        }

        if (dto.getVeiculoId() != null) {
            Veiculo veiculo = repositorioVeiculo.findById(dto.getVeiculoId())
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
            venda.setVeiculo(veiculo);
        }

        venda.getMercadorias().clear();
        if (dto.getMercadorias() != null) {
            for (var mercadoriaDTO : dto.getMercadorias()) {
                Mercadoria mercadoria = repositorioMercadoria.findById(mercadoriaDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Mercadoria não encontrada"));
                venda.getMercadorias().add(mercadoria);
            }
        }

        venda.getServicos().clear();
        if (dto.getServicos() != null) {
            for (var servicoDTO : dto.getServicos()) {
                Servico servico = repositorioServico.findById(servicoDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
                venda.getServicos().add(servico);
            }
        }

        Venda vendaSalva = repositorioVenda.save(venda);

        // Adiciona a venda na coleção da empresa e salva
        empresa.getVendas().add(vendaSalva);
        repositorioEmpresa.save(empresa);

        return VendaMapper.toDTO(vendaSalva);
    }

    public VendaDTO atualizarVenda(VendaDTO dto) {
        Venda venda = repositorioVenda.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Venda não encontrada com id: " + dto.getId()));

        if (dto.getCadastro() != null) {
            venda.setCadastro(dto.getCadastro());
        }

        if (dto.getIdentificacao() != null) {
            venda.setIdentificacao(dto.getIdentificacao());
        }

        if (dto.getClienteId() != null) {
            Usuario cliente = repositorioUsuario.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            venda.setCliente(cliente);
        } else {
            venda.setCliente(null);
        }

        if (dto.getFuncionarioId() != null) {
            Usuario funcionario = repositorioUsuario.findById(dto.getFuncionarioId())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
            venda.setFuncionario(funcionario);
        } else {
            venda.setFuncionario(null);
        }

        if (dto.getVeiculoId() != null) {
            Veiculo veiculo = repositorioVeiculo.findById(dto.getVeiculoId())
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
            venda.setVeiculo(veiculo);
        } else {
            venda.setVeiculo(null);
        }

        if (dto.getEmpresaId() != null) {
            Empresa empresa = repositorioEmpresa.findById(dto.getEmpresaId())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
            venda.setEmpresa(empresa);
        } else {
            venda.setEmpresa(null);
        }

        venda.getMercadorias().clear();
        if (dto.getMercadorias() != null) {
            for (var mercadoriaDTO : dto.getMercadorias()) {
                Mercadoria mercadoria = repositorioMercadoria.findById(mercadoriaDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Mercadoria não encontrada"));
                venda.getMercadorias().add(mercadoria);
            }
        }

        venda.getServicos().clear();
        if (dto.getServicos() != null) {
            for (var servicoDTO : dto.getServicos()) {
                Servico servico = repositorioServico.findById(servicoDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
                venda.getServicos().add(servico);
            }
        }

        Venda vendaAtualizada = repositorioVenda.save(venda);
        return VendaMapper.toDTO(vendaAtualizada);
    }

    @Transactional
    public void deletarVenda(VendaDTO dto) {
        Venda venda = repositorioVenda.findById(dto.getId())
            .orElseThrow(() -> new RuntimeException("Venda não encontrada com id: " + dto.getId()));

        if (venda.getEmpresa() != null) {
            Empresa empresa = venda.getEmpresa();
            empresa.getVendas().remove(venda);
            repositorioEmpresa.save(empresa);
        }

        if (venda.getCliente() != null) {
            Usuario cliente = venda.getCliente();
            cliente.getVendas().remove(venda);
            repositorioUsuario.save(cliente);
        }

        if (venda.getVeiculo() != null) {
            Veiculo veiculo = venda.getVeiculo();
            veiculo.getVendas().remove(venda);
            repositorioVeiculo.save(veiculo);
        }

        if (venda.getMercadorias() != null) {
            venda.getMercadorias().clear(); 
        }

        if (venda.getServicos() != null) {
            venda.getServicos().clear(); 
        }

        if (venda.getVeiculo() != null) {
            venda.setVeiculo(null); // 
        }

        repositorioVenda.delete(venda); 
    }


}
