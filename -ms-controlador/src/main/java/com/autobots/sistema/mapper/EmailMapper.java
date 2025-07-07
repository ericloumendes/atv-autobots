package com.autobots.sistema.mapper;

import com.autobots.sistema.DTO.EmailDTO;
import com.autobots.sistema.entidades.Email;

public class EmailMapper {

    public static EmailDTO toDTO(Email email) {
        if (email == null) {
            return null;
        }
        EmailDTO dto = new EmailDTO();
        dto.setId(email.getId());
        dto.setEndereco(email.getEndereco());
        return dto;
    }

    public static Email toEntity(EmailDTO dto) {
        if (dto == null) {
            return null;
        }
        Email email = new Email();
        email.setId(dto.getId());
        email.setEndereco(dto.getEndereco());
        return email;
    }
}
