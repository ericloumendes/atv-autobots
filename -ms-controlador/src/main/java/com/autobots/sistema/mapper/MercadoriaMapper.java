package com.autobots.sistema.mapper;

import com.autobots.sistema.DTO.MercadoriaDTO;
import com.autobots.sistema.entidades.Mercadoria;

public class MercadoriaMapper {

    public static MercadoriaDTO toDTO(Mercadoria mercadoria) {
        if (mercadoria == null) {
            return null;
        }
        MercadoriaDTO dto = new MercadoriaDTO();
        dto.setId(mercadoria.getId());
        dto.setValidade(mercadoria.getValidade());
        dto.setFabricacao(mercadoria.getFabricacao());
        dto.setCadastro(mercadoria.getCadastro());
        dto.setNome(mercadoria.getNome());
        dto.setQuantidade(mercadoria.getQuantidade());
        dto.setValor(mercadoria.getValor());
        dto.setDescricao(mercadoria.getDescricao());
        return dto;
    }

    public static Mercadoria toEntity(MercadoriaDTO dto) {
        if (dto == null) {
            return null;
        }
        Mercadoria mercadoria = new Mercadoria();
        mercadoria.setId(dto.getId());
        mercadoria.setValidade(dto.getValidade());
        mercadoria.setFabricacao(dto.getFabricacao());
        mercadoria.setCadastro(dto.getCadastro());
        mercadoria.setNome(dto.getNome());
        mercadoria.setQuantidade(dto.getQuantidade());
        mercadoria.setValor(dto.getValor());
        mercadoria.setDescricao(dto.getDescricao());
        return mercadoria;
    }
}
