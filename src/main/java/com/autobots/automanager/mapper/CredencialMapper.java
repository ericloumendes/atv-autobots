package com.autobots.automanager.mapper;

import com.autobots.automanager.DTO.CredencialCodigoBarraDTO;
import com.autobots.automanager.DTO.CredencialDTO;
import com.autobots.automanager.DTO.CredencialUsuarioSenhaDTO;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.entitades.CredencialCodigoBarra;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;

public class CredencialMapper {

    public static CredencialDTO toDTO(Credencial credencial) {
        CredencialDTO dto = new CredencialDTO();
        dto.setId(credencial.getId());
        dto.setCriacao(credencial.getCriacao());
        dto.setUltimoAcesso(credencial.getUltimoAcesso());
        dto.setInativo(credencial.isInativo());
        return dto;
    }

    public static CredencialUsuarioSenhaDTO toCredencialUsuarioSenhaDTO(CredencialUsuarioSenha credencial) {
        CredencialUsuarioSenhaDTO dto = new CredencialUsuarioSenhaDTO();
        dto.setId(credencial.getId());
        dto.setCriacao(credencial.getCriacao());
        dto.setUltimoAcesso(credencial.getUltimoAcesso());
        dto.setInativo(credencial.isInativo());
        dto.setNomeUsuario(credencial.getNomeUsuario());
        dto.setSenha(credencial.getSenha());
        return dto;
    }

    public static CredencialCodigoBarraDTO toCredencialCodigoBarraDTO(CredencialCodigoBarra credencial) {
        CredencialCodigoBarraDTO dto = new CredencialCodigoBarraDTO();
        dto.setId(credencial.getId());
        dto.setCriacao(credencial.getCriacao());
        dto.setUltimoAcesso(credencial.getUltimoAcesso());
        dto.setInativo(credencial.isInativo());
        dto.setCodigo(credencial.getCodigo());
        return dto;
    }
    
    public static Credencial toEntity(CredencialDTO dto) {
        if (dto instanceof CredencialUsuarioSenhaDTO) {
            CredencialUsuarioSenha entidade = new CredencialUsuarioSenha();
            CredencialUsuarioSenhaDTO typedDto = (CredencialUsuarioSenhaDTO) dto;
            entidade.setId(typedDto.getId());
            entidade.setCriacao(typedDto.getCriacao());
            entidade.setUltimoAcesso(typedDto.getUltimoAcesso());
            entidade.setInativo(typedDto.isInativo());
            entidade.setNomeUsuario(typedDto.getNomeUsuario());
            entidade.setSenha(typedDto.getSenha());
            return entidade;
        } else if (dto instanceof CredencialCodigoBarraDTO) {
            CredencialCodigoBarra entidade = new CredencialCodigoBarra();
            CredencialCodigoBarraDTO typedDto = (CredencialCodigoBarraDTO) dto;
            entidade.setId(typedDto.getId());
            entidade.setCriacao(typedDto.getCriacao());
            entidade.setUltimoAcesso(typedDto.getUltimoAcesso());
            entidade.setInativo(typedDto.isInativo());
            entidade.setCodigo(typedDto.getCodigo());
            return entidade;
        } else {
            throw new IllegalArgumentException("Tipo de CredencialDTO desconhecido: " + dto.getClass().getSimpleName());
        }
    }


}
