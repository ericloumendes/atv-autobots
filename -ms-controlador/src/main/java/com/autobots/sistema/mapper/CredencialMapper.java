package com.autobots.sistema.mapper;

import com.autobots.sistema.DTO.CredencialDTO;
import com.autobots.sistema.DTO.CredencialUsuarioSenhaDTO;
import com.autobots.sistema.entidades.Credencial;
import com.autobots.sistema.entidades.CredencialUsuarioSenha;

public class CredencialMapper {

    // Mapeamento base: de entidade para DTO base
    public static CredencialDTO toDTO(Credencial credencial) {
        if (credencial instanceof CredencialUsuarioSenha) {
            return toCredencialUsuarioSenhaDTO((CredencialUsuarioSenha) credencial);
        }

        // Caso haja outras subclasses no futuro
        CredencialDTO dto = new CredencialDTO();
        dto.setId(credencial.getId());
        dto.setCriacao(credencial.getCriacao());
        dto.setUltimoAcesso(credencial.getUltimoAcesso());
        dto.setInativo(credencial.isInativo());
        return dto;
    }

    // Mapeamento específico: de entidade para DTO de usuário/senha
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

    // Mapeamento base: de DTO para entidade base
    public static Credencial toEntity(CredencialDTO dto) {
        if (dto instanceof CredencialUsuarioSenhaDTO) {
            return toCredencialUsuarioSenhaEntity((CredencialUsuarioSenhaDTO) dto);
        }

        // Caso haja outras subclasses no futuro
        Credencial entidade = new Credencial();
        entidade.setId(dto.getId());
        entidade.setCriacao(dto.getCriacao());
        entidade.setUltimoAcesso(dto.getUltimoAcesso());
        entidade.setInativo(dto.isInativo());
        return entidade;
    }

    // Mapeamento específico: de DTO de usuário/senha para entidade
    public static CredencialUsuarioSenha toCredencialUsuarioSenhaEntity(CredencialUsuarioSenhaDTO dto) {
        CredencialUsuarioSenha entidade = new CredencialUsuarioSenha();
        entidade.setId(dto.getId());
        entidade.setCriacao(dto.getCriacao());
        entidade.setUltimoAcesso(dto.getUltimoAcesso());
        entidade.setInativo(dto.isInativo());
        entidade.setNomeUsuario(dto.getNomeUsuario());
        entidade.setSenha(dto.getSenha());
        return entidade;
    }
}
