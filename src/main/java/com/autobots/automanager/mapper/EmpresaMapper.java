package com.autobots.automanager.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.autobots.automanager.DTO.EmpresaDTO;
import com.autobots.automanager.DTO.TelefoneDTO;
import com.autobots.automanager.DTO.EnderecoDTO;
import com.autobots.automanager.DTO.UsuarioDTO;
import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.DTO.ServicoDTO;
import com.autobots.automanager.DTO.VendaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;


public class EmpresaMapper {

    public static EmpresaDTO toDTO(Empresa empresa) {
        if (empresa == null) {
            return null;
        }
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setRazaoSocial(empresa.getRazaoSocial());
        dto.setNomeFantasia(empresa.getNomeFantasia());

        Set<Telefone> telefones = empresa.getTelefones();
        if (telefones != null) {
            Set<TelefoneDTO> telefonesDTO = telefones.stream()
                .map(TelefoneMapper::toDTO)
                .collect(Collectors.toSet());
            dto.setTelefones(telefonesDTO);
        }

        Endereco endereco = empresa.getEndereco();
        if (endereco != null) {
            dto.setEndereco(EnderecoMapper.toDTO(endereco));
        }

        dto.setCadastro(empresa.getCadastro());

        Set<Usuario> usuarios = empresa.getUsuarios();
        if (usuarios != null) {
            Set<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toSet());
            dto.setUsuarios(usuariosDTO);
        }

        Set<Mercadoria> mercadorias = empresa.getMercadorias();
        if (mercadorias != null) {
            Set<MercadoriaDTO> mercadoriasDTO = mercadorias.stream()
                .map(MercadoriaMapper::toDTO)
                .collect(Collectors.toSet());
            dto.setMercadorias(mercadoriasDTO);
        }

        Set<Servico> servicos = empresa.getServicos();
        if (servicos != null) {
            Set<ServicoDTO> servicosDTO = servicos.stream()
                .map(ServicoMapper::toDTO)
                .collect(Collectors.toSet());
            dto.setServicos(servicosDTO);
        }

        Set<Venda> vendas = empresa.getVendas();
        if (vendas != null) {
            Set<VendaDTO> vendasDTO = vendas.stream()
                .map(VendaMapper::toDTO)
                .collect(Collectors.toSet());
            dto.setVendas(vendasDTO);
        }

        return dto;
    }

    public static Empresa toEntity(EmpresaDTO dto) {
        if (dto == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(dto.getId());
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setNomeFantasia(dto.getNomeFantasia());

        Set<TelefoneDTO> telefonesDTO = dto.getTelefones();
        if (telefonesDTO != null) {
            Set<Telefone> telefones = telefonesDTO.stream()
                .map(TelefoneMapper::toEntity)
                .collect(Collectors.toSet());
            empresa.setTelefones(telefones);
        }

        EnderecoDTO enderecoDTO = dto.getEndereco();
        if (enderecoDTO != null) {
            empresa.setEndereco(EnderecoMapper.toEntity(enderecoDTO));
        }

        empresa.setCadastro(dto.getCadastro());

        Set<UsuarioDTO> usuariosDTO = dto.getUsuarios();
        if (usuariosDTO != null) {
            Set<Usuario> usuarios = usuariosDTO.stream()
                .map(UsuarioMapper::toEntity)
                .collect(Collectors.toSet());
            empresa.setUsuarios(usuarios);
        }

        Set<MercadoriaDTO> mercadoriasDTO = dto.getMercadorias();
        if (mercadoriasDTO != null) {
            Set<Mercadoria> mercadorias = mercadoriasDTO.stream()
                .map(MercadoriaMapper::toEntity)
                .collect(Collectors.toSet());
            empresa.setMercadorias(mercadorias);
        }

        Set<ServicoDTO> servicosDTO = dto.getServicos();
        if (servicosDTO != null) {
            Set<Servico> servicos = servicosDTO.stream()
                .map(ServicoMapper::toEntity)
                .collect(Collectors.toSet());
            empresa.setServicos(servicos);
        }

        Set<VendaDTO> vendasDTO = dto.getVendas();
        if (vendasDTO != null) {
            Set<Venda> vendas = vendasDTO.stream()
                .map(VendaMapper::toEntity)
                .collect(Collectors.toSet());
            empresa.setVendas(vendas);
        }

        return empresa;
    }
}
