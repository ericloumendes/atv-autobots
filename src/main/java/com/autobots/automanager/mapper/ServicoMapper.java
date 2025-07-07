package com.autobots.automanager.mapper;

import com.autobots.automanager.DTO.ServicoDTO;
import com.autobots.automanager.entidades.Servico;

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
