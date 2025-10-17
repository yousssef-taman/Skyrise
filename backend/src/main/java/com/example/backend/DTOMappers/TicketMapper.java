package com.example.backend.DTOMappers;

import com.example.backend.DTOs.TicketDTO;
import com.example.backend.Entities.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketDTO toDTO(Ticket entity);
    Ticket toEntity(TicketDTO dto);
}
