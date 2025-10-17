package com.example.backend.DTOMappers;

import com.example.backend.DTOs.FlightSearchResponse;
import com.example.backend.Entities.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightSearchMapper {
    FlightSearchResponse toDTO(Flight entity);
    Flight toEntity(FlightSearchResponse dto);
}
