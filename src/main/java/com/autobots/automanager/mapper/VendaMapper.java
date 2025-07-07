package com.autobots.automanager.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.DTO.ServicoDTO;
import com.autobots.automanager.DTO.VendaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;

public class VendaMapper {

    public static VendaDTO toDTO(Venda venda) {
        if (venda == null) {
            return null;
        }
        VendaDTO dto = new VendaDTO();
        dto.setId(venda.getId());
        dto.setCadastro(venda.getCadastro());
        dto.setIdentificacao(venda.getIdentificacao());

        if (venda.getCliente() != null) {
            dto.setClienteId(venda.getCliente().getId());
        }
        if (venda.getFuncionario() != null) {
            dto.setFuncionarioId(venda.getFuncionario().getId());
        }
        if (venda.getVeiculo() != null) {
            dto.setVeiculoId(venda.getVeiculo().getId());
        }
        

        if (venda.getMercadorias() != null) {
            Set<MercadoriaDTO> mercadoriasDTO = venda.getMercadorias().stream()
                .map(MercadoriaMapper::toDTO)
                .collect(Collectors.toSet());
            dto.setMercadorias(mercadoriasDTO);
        }

        if (venda.getServicos() != null) {
            Set<ServicoDTO> servicosDTO = venda.getServicos().stream()
                .map(ServicoMapper::toDTO)
                .collect(Collectors.toSet());
            dto.setServicos(servicosDTO);
        }

        return dto;
    }

    public static Venda toEntity(VendaDTO dto) {
        if (dto == null) {
            return null;
        }
        Venda venda = new Venda();
        venda.setId(dto.getId());
        venda.setCadastro(dto.getCadastro());
        venda.setIdentificacao(dto.getIdentificacao());

        if (dto.getClienteId() != null) {
            Usuario cliente = new Usuario();
            cliente.setId(dto.getClienteId());
            venda.setCliente(cliente);
        }

        if (dto.getFuncionarioId() != null) {
            Usuario funcionario = new Usuario();
            funcionario.setId(dto.getFuncionarioId());
            venda.setFuncionario(funcionario);
        }

        if (dto.getVeiculoId() != null) {
            Veiculo veiculo = new Veiculo();
            veiculo.setId(dto.getVeiculoId());
            venda.setVeiculo(veiculo);
        }


        if (dto.getMercadorias() != null) {
            Set<Mercadoria> mercadorias = dto.getMercadorias().stream()
                .map(MercadoriaMapper::toEntity)
                .collect(Collectors.toSet());
            venda.setMercadorias(mercadorias);
        }

        if (dto.getServicos() != null) {
            Set<Servico> servicos = dto.getServicos().stream()
                .map(ServicoMapper::toEntity)
                .collect(Collectors.toSet());
            venda.setServicos(servicos);
        }
        return venda;
    }
}
