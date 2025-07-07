package com.autobots.automanager.mapper;

import com.autobots.automanager.DTO.TelefoneDTO;
import com.autobots.automanager.entitades.Telefone;

public class TelefoneMapper {

    public static TelefoneDTO toDTO(Telefone telefone) {
        if (telefone == null) {
            return null;
        }
        TelefoneDTO dto = new TelefoneDTO();
        dto.setId(telefone.getId());
        dto.setDdd(telefone.getDdd());
        dto.setNumero(telefone.getNumero());
        return dto;
    }

    public static Telefone toEntity(TelefoneDTO dto) {
        if (dto == null) {
            return null;
        }
        Telefone telefone = new Telefone();
        telefone.setId(dto.getId());
        telefone.setDdd(dto.getDdd());
        telefone.setNumero(dto.getNumero());
        return telefone;
    }
}
