package com.example.backend.DTOMappers;

import com.example.backend.DTOs.FlightLegDTO;
import com.example.backend.Entities.FlightLeg;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightLegMapper {
    FlightLegDTO toDTO(FlightLeg entity);
    FlightLeg toEntity(FlightLegDTO dto);
}
