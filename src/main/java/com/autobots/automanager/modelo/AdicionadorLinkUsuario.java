package com.autobots.automanager.modelo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.DocumentoControle;
import com.autobots.automanager.controle.UsuarioControle;
import com.autobots.automanager.DTO.DocumentoDTO;
import com.autobots.automanager.DTO.UsuarioDTO;

import java.util.List;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<UsuarioDTO> {

    @Override
    public void adicionadorLink(List<UsuarioDTO> lista) {
        for (UsuarioDTO usuarioDTO : lista) {
        	adicionadorLinkParaUsuario(usuarioDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(UsuarioDTO usuarioDTO) {
    	adicionadorLinkParaUsuario(usuarioDTO, false);
    }

    private void adicionadorLinkParaUsuario(UsuarioDTO usuarioDTO, boolean isList) {
        if (usuarioDTO != null) {
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControle.class).obterUsuario(usuarioDTO.getId()))
                        .withSelfRel();
                usuarioDTO.add(linkProprio);
            }
            /*
            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControle.class).deletarUsuario(usuarioDTO))
                    .withRel("deletar");
            usuarioDTO.add(linkDeletar);

            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControle.class).atualizarUsuario(usuarioDTO))
                    .withRel("atualizar");

            usuarioDTO.add(linkAtualizar);*/

            if (!isList) {
                Link linkMercadorias = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioControle.class).obterUsuarios())
                        .withRel("usuarios");
                usuarioDTO.add(linkMercadorias);
            }
        }
    }
}
