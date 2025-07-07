package com.autobots.automanager.DTO;

import com.autobots.automanager.DTO.UsuarioDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

public class UsuarioDTOSerializer extends JsonSerializer<UsuarioDTO> {

    @Override
    public void serialize(UsuarioDTO usuarioDTO, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        // Serializa o campo 'id' de forma obrigatória
        gen.writeNumberField("id", usuarioDTO.getId());
        gen.writeStringField("nome", usuarioDTO.getNome());

        // Se 'nomeSocial' não for nulo, inclui no JSON
        if (usuarioDTO.getNomeSocial() != null) {
            gen.writeStringField("nomeSocial", usuarioDTO.getNomeSocial());
        }

        // Se 'perfis' não for nulo ou vazio, inclui no JSON
        if (usuarioDTO.getPerfil() != null) {
            gen.writeObjectField("perfis", usuarioDTO.getPerfil());
        }

        // Se 'endereco' não for nulo, inclui no JSON
        if (usuarioDTO.getEndereco() != null) {
            gen.writeObjectField("endereco", usuarioDTO.getEndereco());
        }

        // Se 'documentos' não for nulo ou vazio, inclui no JSON
        if (usuarioDTO.getDocumentos() != null && !usuarioDTO.getDocumentos().isEmpty()) {
            gen.writeObjectField("documentos", usuarioDTO.getDocumentos());
        }

        // Se 'emails' não for nulo ou vazio, inclui no JSON
        if (usuarioDTO.getEmails() != null && !usuarioDTO.getEmails().isEmpty()) {
            gen.writeObjectField("emails", usuarioDTO.getEmails());
        }

        // Se 'credenciais' não for nulo ou vazio, inclui no JSON
        if (usuarioDTO.getCredencial() != null) {
            gen.writeObjectField("credenciais", usuarioDTO.getCredencial());
        }

        // Se 'telefones' não for nulo ou vazio, inclui no JSON
        if (usuarioDTO.getTelefones() != null && !usuarioDTO.getTelefones().isEmpty()) {
            gen.writeObjectField("telefones", usuarioDTO.getTelefones());
        }

        // Se 'mercadorias' não for nulo ou vazio, inclui no JSON
        if (usuarioDTO.getMercadorias() != null && !usuarioDTO.getMercadorias().isEmpty()) {
            gen.writeObjectField("mercadorias", usuarioDTO.getMercadorias());
        }

        // Se 'vendas' não for nulo ou vazio, inclui no JSON
        if (usuarioDTO.getVendas() != null && !usuarioDTO.getVendas().isEmpty()) {
            gen.writeObjectField("vendas", usuarioDTO.getVendas());
        }

        // Se 'veiculos' não for nulo ou vazio, inclui no JSON
        if (usuarioDTO.getVeiculos() != null && !usuarioDTO.getVeiculos().isEmpty()) {
            gen.writeObjectField("veiculos", usuarioDTO.getVeiculos());
        }

        // Finaliza o objeto
        gen.writeEndObject();
    }
}