package com.example.backend.DTOMappers;

import com.example.backend.DTOs.AdminFlightDTO;
import com.example.backend.Entities.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminFlightMapper {
    AdminFlightMapper INSTANCE = Mappers.getMapper(AdminFlightMapper.class);

    @Mapping(target = "source", expression = "java(entity.getFlightLegs().get(0).getDepartureAirport().getAirportCity())")
    @Mapping(target = "destination", expression = "java(entity.getFlightLegs().get(entity.getFlightLegs().size()-1).getArrivalAirport().getAirportCity())")
    @Mapping(target = "departureTime", expression = "java(entity.getFlightLegs().get(0).getDepartureTime())")
    @Mapping(target = "arrivalTime", expression = "java(entity.getFlightLegs().get(entity.getFlightLegs().size()-1).getArrivalTime())")
    AdminFlightDTO toDTO(Flight entity);

    Flight toEntity(AdminFlightDTO dto);
}