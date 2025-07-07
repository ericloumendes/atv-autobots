package com.autobots.sistema.modelo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.sistema.DTO.VeiculoDTO;
import com.autobots.sistema.controle.VeiculoControle;

import java.util.List;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<VeiculoDTO> {

    @Override
    public void adicionadorLink(List<VeiculoDTO> lista) {
        for (VeiculoDTO veiculoDTO : lista) {
            // Para a lista de emails, adicionamos o link 'self' para cada email,
            // mas não o link para todos os emails
            adicionadorLinkParaMercadoria(veiculoDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(VeiculoDTO veiculoDTO) {
        // Quando um email específico for requisitado, adicionamos o link para a lista de emails,
        // mas não o link 'self' para o próprio email
        adicionadorLinkParaMercadoria(veiculoDTO, false);
    }

    private void adicionadorLinkParaMercadoria(VeiculoDTO veiculoDTO, boolean isList) {
        if (veiculoDTO != null) {
            // Se isList for true, adiciona o link 'self' para o email
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoControle.class).obterVeiculo(veiculoDTO.getId()))
                        .withSelfRel();
                veiculoDTO.add(linkProprio);
            }

            // Link para deletar o email
            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoControle.class).deletarVeiculo(veiculoDTO))
                    .withRel("deletar");
            veiculoDTO.add(linkDeletar);

            // Link para atualizar o email
            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoControle.class).atualizarVeiculo(veiculoDTO))
                    .withRel("atualizar");

            veiculoDTO.add(linkAtualizar);

            // Se não for uma lista (ou seja, se for um email específico), adiciona o link para a lista de todos os emails
            if (!isList) {
                Link linkMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VeiculoControle.class).obterVeiculos())
                        .withRel("veiculos");
                veiculoDTO.add(linkMercadorias);
            }
        }
    }
}
