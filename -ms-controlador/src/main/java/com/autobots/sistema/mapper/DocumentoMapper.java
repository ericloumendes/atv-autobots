package com.autobots.sistema.mapper;

import com.autobots.sistema.DTO.DocumentoDTO;
import com.autobots.sistema.entidades.Documento;

public class DocumentoMapper {

    public static DocumentoDTO toDTO(Documento documento) {
        if (documento == null) {
            return null;
        }
        DocumentoDTO dto = new DocumentoDTO();
        dto.setId(documento.getId());
        dto.setTipo(documento.getTipo());
        dto.setDataEmissao(documento.getDataEmissao());
        dto.setNumero(documento.getNumero());
        return dto;
    }

    public static Documento toEntity(DocumentoDTO dto) {
        if (dto == null) {
            return null;
        }
        Documento documento = new Documento();
        documento.setId(dto.getId());
        documento.setTipo(dto.getTipo());
        documento.setDataEmissao(dto.getDataEmissao());
        documento.setNumero(dto.getNumero());
        return documento;
    }
}
