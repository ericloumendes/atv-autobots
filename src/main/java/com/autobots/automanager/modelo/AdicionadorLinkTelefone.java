package com.autobots.automanager.modelo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.ServicoControle;
import com.autobots.automanager.controle.TelefoneControle;
import com.autobots.automanager.DTO.ServicoDTO;
import com.autobots.automanager.DTO.TelefoneDTO;

import java.util.List;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<TelefoneDTO> {

    @Override
    public void adicionadorLink(List<TelefoneDTO> lista) {
        for (TelefoneDTO telefoneDTO : lista) {
            adicionadorLinkParaTelefone(telefoneDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(TelefoneDTO telefoneDTO) {
        adicionadorLinkParaTelefone(telefoneDTO, false);
    }

    private void adicionadorLinkParaTelefone(TelefoneDTO telefoneDTO, boolean isList) {
        if (telefoneDTO != null) {
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneControle.class).obterTelefone(telefoneDTO.getId()))
                        .withSelfRel();
                telefoneDTO.add(linkProprio);
            }

            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneControle.class).deletarTelefone(telefoneDTO))
                    .withRel("deletar");
            telefoneDTO.add(linkDeletar);

            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneControle.class).atualizarTelefone(telefoneDTO))
                    .withRel("atualizar");

            telefoneDTO.add(linkAtualizar);

            if (!isList) {
                Link linkMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TelefoneControle.class).obterTelefones())
                        .withRel("telefones");
                telefoneDTO.add(linkMercadorias);
            }
        }
    }
}
