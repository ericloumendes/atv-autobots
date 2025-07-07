package com.autobots.sistema.mapper;

import com.autobots.sistema.DTO.ServicoDTO;
import com.autobots.sistema.entidades.Servico;

public class ServicoMapper {

    public static ServicoDTO toDTO(Servico servico) {
        if (servico == null) {
            return null;
        }
        ServicoDTO dto = new ServicoDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setValor(servico.getValor());
        dto.setDescricao(servico.getDescricao());
        return dto;
    }

    public static Servico toEntity(ServicoDTO dto) {
        if (dto == null) {
            return null;
        }
        Servico servico = new Servico();
        servico.setId(dto.getId());
        servico.setNome(dto.getNome());
        servico.setValor(dto.getValor());
        servico.setDescricao(dto.getDescricao());
        return servico;
    }
}
