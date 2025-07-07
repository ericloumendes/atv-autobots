package com.autobots.automanager.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.autobots.automanager.DTO.*;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.mapper.*;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setNomeSocial(usuario.getNomeSocial());
        dto.setPerfil(usuario.getPerfil());

        dto.setEndereco(EnderecoMapper.toDTO(usuario.getEndereco()));

        dto.setDocumentos(usuario.getDocumentos().stream()
            .map(DocumentoMapper::toDTO)
            .collect(Collectors.toSet()));

        dto.setEmails(usuario.getEmails().stream()
            .map(EmailMapper::toDTO)
            .collect(Collectors.toSet()));

        dto.setTelefones(usuario.getTelefones().stream()
            .map(TelefoneMapper::toDTO)
            .collect(Collectors.toSet()));

        dto.setMercadorias(usuario.getMercadorias().stream()
            .map(MercadoriaMapper::toDTO)
            .collect(Collectors.toSet()));

        dto.setVendas(usuario.getVendas().stream()
            .map(VendaMapper::toDTO)
            .collect(Collectors.toSet()));

        dto.setVeiculos(usuario.getVeiculos().stream()
            .map(VeiculoMapper::toDTO)
            .collect(Collectors.toSet()));

        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setNomeSocial(dto.getNomeSocial());
        usuario.setPerfil(dto.getPerfil());

        if (dto.getEndereco() != null) {
            usuario.setEndereco(EnderecoMapper.toEntity(dto.getEndereco()));
        }

        if (dto.getDocumentos() != null) {
            usuario.setDocumentos(dto.getDocumentos().stream()
                .map(DocumentoMapper::toEntity)
                .collect(Collectors.toSet()));
        }

        if (dto.getEmails() != null) {
            usuario.setEmails(dto.getEmails().stream()
                .map(EmailMapper::toEntity)
                .collect(Collectors.toSet()));
        }


        if (dto.getTelefones() != null) {
            usuario.setTelefones(dto.getTelefones().stream()
                .map(TelefoneMapper::toEntity)
                .collect(Collectors.toSet()));
        }

        if (dto.getMercadorias() != null) {
            usuario.setMercadorias(dto.getMercadorias().stream()
                .map(MercadoriaMapper::toEntity)
                .collect(Collectors.toSet()));
        }

        if (dto.getVendas() != null) {
            usuario.setVendas(dto.getVendas().stream()
                .map(VendaMapper::toEntity)
                .collect(Collectors.toSet()));
        }

        if (dto.getVeiculos() != null) {
            usuario.setVeiculos(dto.getVeiculos().stream()
                .map(VeiculoMapper::toEntity)
                .collect(Collectors.toSet()));
        }

        return usuario;
    }

}
