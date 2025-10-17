package com.example.backend.DTOMappers;


import com.example.backend.DTOs.FlightLegUpdateDTO;
import com.example.backend.Entities.FlightLeg;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightLegUpdateMapper {
	FlightLegUpdateDTO toDTO(FlightLeg entity);
	FlightLeg toEntity(FlightLeg dto);
}
