package com.autobots.automanager.mapper;

import com.autobots.automanager.DTO.EmailDTO;
import com.autobots.automanager.entidades.Email;

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
