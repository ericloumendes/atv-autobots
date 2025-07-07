package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.EmpresaDTO;
import com.autobots.automanager.controle.EmpresaControle;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<EmpresaDTO> {
    @Override
    public void adicionadorLink(List<EmpresaDTO> lista) {
        for (EmpresaDTO empresaDTO : lista) {
        	adicionadorLinkParaEmpresa(empresaDTO, true); 
        }
    }

    @Override
    public void adicionadorLink(EmpresaDTO empresaDTO) {
    	adicionadorLinkParaEmpresa(empresaDTO, false);
    }

    private void adicionadorLinkParaEmpresa(EmpresaDTO empresaDTO, boolean isList) {
        if (empresaDTO != null) {
            if (isList) {
                Link linkProprio = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).obterEmpresa(empresaDTO.getId()))
                        .withSelfRel();
                empresaDTO.add(linkProprio);
            }
            
            Link linkDeletar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).deletarEmpresa(empresaDTO))
                    .withRel("deletar");
            empresaDTO.add(linkDeletar);

            Link linkAtualizar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).atualizarEmpresa(empresaDTO))
                    .withRel("atualizar");

            empresaDTO.add(linkAtualizar);

            if (!isList) {
                Link linkEmpresas = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmpresaControle.class).obterEmpresas())
                        .withRel("empresass");
                empresaDTO.add(linkEmpresas);
            }
        }
    }
}
