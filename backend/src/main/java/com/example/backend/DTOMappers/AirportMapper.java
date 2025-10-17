package com.example.backend.DTOMappers;

import com.example.backend.DTOs.AirportDTO;
import com.example.backend.Entities.Airport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportDTO toDTO(Airport entity);
    Airport toEntity(AirportDTO dto);
}