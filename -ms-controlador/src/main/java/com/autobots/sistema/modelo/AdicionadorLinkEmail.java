package com.autobots.sistema.modelo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.sistema.DTO.EmailDTO;
import com.autobots.sistema.controle.EmailControle;

import java.util.List;

@Component
public class AdicionadorLinkEmail implements AdicionadorLink<EmailDTO> {

    @Override
    public void adicionadorLink(List<EmailDTO> lista) {
        for (EmailDTO emailDTO : lista) {
            // Para a lista de emails, adicionamos o link 'self' para cada email,
            // mas não o link para todos os emails
            adicionadorLinkParaEmail(emailDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(EmailDTO emailDTO) {
        // Quando um email específico for requisitado, adicionamos o link para a lista de emails,
        // mas não o link 'self' para o próprio email
        adicionadorLinkParaEmail(emailDTO, false);
    }

    private void adicionadorLinkParaEmail(EmailDTO emailDTO, boolean isList) {
        if (emailDTO != null) {
            // Se isList for true, adiciona o link 'self' para o email
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailControle.class).obterEmail(emailDTO.getId()))
                        .withSelfRel();
                emailDTO.add(linkProprio);
            }

            // Link para deletar o email
            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailControle.class).deletarEmail(emailDTO))
                    .withRel("deletar");
            emailDTO.add(linkDeletar);

            // Link para atualizar o email
            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailControle.class).atualizarEmail(emailDTO))
                    .withRel("atualizar");

            emailDTO.add(linkAtualizar);

            // Se não for uma lista (ou seja, se for um email específico), adiciona o link para a lista de todos os emails
            if (!isList) {
                Link linkEmails = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmailControle.class).obterEmails())
                        .withRel("emails");
                emailDTO.add(linkEmails);
            }
        }
    }
}
