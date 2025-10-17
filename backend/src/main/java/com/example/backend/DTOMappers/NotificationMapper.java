package com.example.backend.DTOMappers;

import com.example.backend.DTOs.NotificationDTO;
import com.example.backend.Entities.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationDTO toDTO(Notification entity);
    Notification toEntity(NotificationDTO dto);
}
