package com.autobots.automanager.modelo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.EmailControle;
import com.autobots.automanager.controle.MercadoriaControle;
import com.autobots.automanager.DTO.EmailDTO;
import com.autobots.automanager.DTO.MercadoriaDTO;

import java.util.List;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<MercadoriaDTO> {

    @Override
    public void adicionadorLink(List<MercadoriaDTO> lista) {
        for (MercadoriaDTO mercadoriaDTO : lista) {
            // Para a lista de emails, adicionamos o link 'self' para cada email,
            // mas não o link para todos os emails
            adicionadorLinkParaMercadoria(mercadoriaDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(MercadoriaDTO mercadoriaDTO) {
        // Quando um email específico for requisitado, adicionamos o link para a lista de emails,
        // mas não o link 'self' para o próprio email
        adicionadorLinkParaMercadoria(mercadoriaDTO, false);
    }

    private void adicionadorLinkParaMercadoria(MercadoriaDTO mercadoriaDTO, boolean isList) {
        if (mercadoriaDTO != null) {
            // Se isList for true, adiciona o link 'self' para o email
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).obterMercadoria(mercadoriaDTO.getId()))
                        .withSelfRel();
                mercadoriaDTO.add(linkProprio);
            }

            // Link para deletar o email
            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).deletarMercadoria(mercadoriaDTO))
                    .withRel("deletar");
            mercadoriaDTO.add(linkDeletar);

            // Link para atualizar o email
            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).atualizarMercadoria(mercadoriaDTO))
                    .withRel("atualizar");

            mercadoriaDTO.add(linkAtualizar);

            // Se não for uma lista (ou seja, se for um email específico), adiciona o link para a lista de todos os emails
            if (!isList) {
                Link linkMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MercadoriaControle.class).obterMercadorias())
                        .withRel("mercadorias");
                mercadoriaDTO.add(linkMercadorias);
            }
        }
    }
}
