package com.autobots.automanager.mapper;

import com.autobots.automanager.DTO.EnderecoDTO;
import com.autobots.automanager.entitades.Endereco;

public class EnderecoMapper {

    public static EnderecoDTO toDTO(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(endereco.getId());
        dto.setEstado(endereco.getEstado());
        dto.setCidade(endereco.getCidade());
        dto.setBairro(endereco.getBairro());
        dto.setRua(endereco.getRua());
        dto.setNumero(endereco.getNumero());
        dto.setCodigoPostal(endereco.getCodigoPostal());
        dto.setInformacoesAdicionais(endereco.getInformacoesAdicionais());
        return dto;
    }

    public static Endereco toEntity(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(dto.getId());
        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setCodigoPostal(dto.getCodigoPostal());
        endereco.setInformacoesAdicionais(dto.getInformacoesAdicionais());
        return endereco;
    }
}
