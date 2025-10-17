package com.example.backend.DTOMappers;

import com.example.backend.DTOs.PassengerDTO;
import com.example.backend.Entities.Passenger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerDTO toDTO(Passenger entity);
    Passenger toEntity(PassengerDTO dto);
}
