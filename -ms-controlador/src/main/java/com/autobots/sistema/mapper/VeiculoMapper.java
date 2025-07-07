package com.autobots.sistema.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.autobots.sistema.DTO.UsuarioDTO;
import com.autobots.sistema.DTO.VeiculoDTO;
import com.autobots.sistema.DTO.VendaDTO;
import com.autobots.sistema.entidades.Veiculo;

public class VeiculoMapper {

	public static VeiculoDTO toDTO(Veiculo veiculo) {
	    if (veiculo == null) {
	        return null;
	    }
	    VeiculoDTO dto = new VeiculoDTO();
	    dto.setId(veiculo.getId());
	    dto.setTipo(veiculo.getTipo());
	    dto.setModelo(veiculo.getModelo());
	    dto.setPlaca(veiculo.getPlaca());
	    
	    if (veiculo.getProprietario() != null) {
	        dto.setProprietarioId(veiculo.getProprietario().getId());
	    }
	    
	    return dto;
	}


    public static Veiculo toEntity(VeiculoDTO dto) {
        if (dto == null) {
            return null;
        }
        Veiculo veiculo = new Veiculo();
        veiculo.setId(dto.getId());
        veiculo.setTipo(dto.getTipo());
        veiculo.setModelo(dto.getModelo());
        veiculo.setPlaca(dto.getPlaca());
        
        return veiculo;
    }
}
