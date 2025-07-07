package com.autobots.sistema.modelo;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.sistema.DTO.EnderecoDTO;
import com.autobots.sistema.controle.EnderecoControle;

import java.util.List;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<EnderecoDTO> {

    @Override
    public void adicionadorLink(List<EnderecoDTO> lista) {
        for (EnderecoDTO enderecoDTO : lista) {
        	adicionadorLinkParaEndereco(enderecoDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(EnderecoDTO enderecoDTO) {
    	adicionadorLinkParaEndereco(enderecoDTO, false);
    }

    private void adicionadorLinkParaEndereco(EnderecoDTO enderecoDTO, boolean isList) {
        if (enderecoDTO != null) {
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterEndereco(enderecoDTO.getId()))
                        .withSelfRel();
                enderecoDTO.add(linkProprio);
            }
            
            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).deletarEndereco(enderecoDTO))
                    .withRel("deletar");
            enderecoDTO.add(linkDeletar);

            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).atualizarEndereco(enderecoDTO))
                    .withRel("atualizar");

            enderecoDTO.add(linkAtualizar);

            if (!isList) {
                Link linkMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EnderecoControle.class).obterEnderecos())
                        .withRel("enderecos");
                enderecoDTO.add(linkMercadorias);
            }
        }
    }
}
