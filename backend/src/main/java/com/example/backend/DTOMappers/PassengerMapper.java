package com.example.backend.DTOMappers;

import com.example.backend.DTOs.PassengerDTO;
import com.example.backend.Entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerDTO toDTO(User entity);
    User toEntity(PassengerDTO dto);
}
