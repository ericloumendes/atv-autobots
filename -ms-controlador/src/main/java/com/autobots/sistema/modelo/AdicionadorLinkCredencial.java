package com.autobots.sistema.modelo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.sistema.DTO.CredencialDTO;
import com.autobots.sistema.controle.CredencialControle;

import java.util.List;

@Component
public class AdicionadorLinkCredencial implements AdicionadorLink<CredencialDTO> {

    @Override
    public void adicionadorLink(List<CredencialDTO> lista) {
        for (CredencialDTO credencialDTO : lista) {
            adicionadorLinkParaDocumento(credencialDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(CredencialDTO credencialDTO) {
        adicionadorLinkParaDocumento(credencialDTO, false);
    }

    private void adicionadorLinkParaDocumento(CredencialDTO credencialDTO, boolean isList) {
        if (credencialDTO != null) {
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialControle.class).obterCredencial(credencialDTO.getId()))
                        .withSelfRel();
                credencialDTO.add(linkProprio);
            }

            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialControle.class).deletarCredencial(credencialDTO))
                    .withRel("deletar");
            credencialDTO.add(linkDeletar);

            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialControle.class).atualizarCredencial(credencialDTO))
                    .withRel("atualizar");

            credencialDTO.add(linkAtualizar);

            if (!isList) {
                Link linkMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CredencialControle.class).obterCredenciais())
                        .withRel("credenciais");
                credencialDTO.add(linkMercadorias);
            }
        }
    }
}
