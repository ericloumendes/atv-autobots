package com.autobots.sistema.modelo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.sistema.DTO.VeiculoDTO;
import com.autobots.sistema.DTO.VendaDTO;
import com.autobots.sistema.controle.VeiculoControle;
import com.autobots.sistema.controle.VendaControle;

import java.util.List;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<VendaDTO> {

    @Override
    public void adicionadorLink(List<VendaDTO> lista) {
        for (VendaDTO vendaDTO : lista) {
        	adicionadorLinkParaVenda(vendaDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(VendaDTO vendaDTO) {
    	adicionadorLinkParaVenda(vendaDTO, false);
    }

    private void adicionadorLinkParaVenda(VendaDTO vendaDTO, boolean isList) {
        if (vendaDTO != null) {
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).obterVenda(vendaDTO.getId()))
                        .withSelfRel();
                vendaDTO.add(linkProprio);
            }

            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).deletarVenda(vendaDTO))
                    .withRel("deletar");
            vendaDTO.add(linkDeletar);

            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).atualizarVenda(vendaDTO))
                    .withRel("atualizar");

            vendaDTO.add(linkAtualizar);

            if (!isList) {
                Link linkMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendaControle.class).obterVendas())
                        .withRel("vendas");
                vendaDTO.add(linkMercadorias);
            }
        }
    }
}
