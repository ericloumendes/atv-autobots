package com.autobots.sistema.modelo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.sistema.DTO.DocumentoDTO;
import com.autobots.sistema.controle.DocumentoControle;

import java.util.List;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<DocumentoDTO> {

    @Override
    public void adicionadorLink(List<DocumentoDTO> lista) {
        for (DocumentoDTO documentoDTO : lista) {
            adicionadorLinkParaDocumento(documentoDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(DocumentoDTO documentoDTO) {
        adicionadorLinkParaDocumento(documentoDTO, false);
    }

    private void adicionadorLinkParaDocumento(DocumentoDTO documentoDTO, boolean isList) {
        if (documentoDTO != null) {
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterDocumento(documentoDTO.getId()))
                        .withSelfRel();
                documentoDTO.add(linkProprio);
            }

            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).deletarDocumento(documentoDTO))
                    .withRel("deletar");
            documentoDTO.add(linkDeletar);

            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).atualizarDocumento(documentoDTO))
                    .withRel("atualizar");

            documentoDTO.add(linkAtualizar);

            if (!isList) {
                Link linkMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentoControle.class).obterDocumentos())
                        .withRel("documentos");
                documentoDTO.add(linkMercadorias);
            }
        }
    }
}
