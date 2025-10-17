package com.example.backend.DTOMappers;

import com.example.backend.DTOs.ReservationDTO;
import com.example.backend.Entities.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDTO toDTO(Reservation entity);
    Reservation toEntity(ReservationDTO dto);
}