package com.autobots.sistema.modelo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.sistema.DTO.ServicoDTO;
import com.autobots.sistema.controle.ServicoControle;

import java.util.List;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<ServicoDTO> {

    @Override
    public void adicionadorLink(List<ServicoDTO> lista) {
        for (ServicoDTO servicoDTO : lista) {
            // Para a lista de emails, adicionamos o link 'self' para cada email,
            // mas não o link para todos os emails
            adicionadorLinkParaMercadoria(servicoDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(ServicoDTO servicoDTO) {
        // Quando um email específico for requisitado, adicionamos o link para a lista de emails,
        // mas não o link 'self' para o próprio email
        adicionadorLinkParaMercadoria(servicoDTO, false);
    }

    private void adicionadorLinkParaMercadoria(ServicoDTO servicoDTO, boolean isList) {
        if (servicoDTO != null) {
            // Se isList for true, adiciona o link 'self' para o email
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoControle.class).obterServico(servicoDTO.getId()))
                        .withSelfRel();
                servicoDTO.add(linkProprio);
            }

            // Link para deletar o email
            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoControle.class).deletarServico(servicoDTO))
                    .withRel("deletar");
            servicoDTO.add(linkDeletar);

            // Link para atualizar o email
            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoControle.class).atualizarServico(servicoDTO))
                    .withRel("atualizar");

            servicoDTO.add(linkAtualizar);

            // Se não for uma lista (ou seja, se for um email específico), adiciona o link para a lista de todos os emails
            if (!isList) {
                Link linkMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ServicoControle.class).obterServicos())
                        .withRel("servicos");
                servicoDTO.add(linkMercadorias);
            }
        }
    }
}
