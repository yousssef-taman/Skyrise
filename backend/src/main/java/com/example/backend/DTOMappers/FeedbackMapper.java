package com.example.backend.DTOMappers;

import com.example.backend.DTOs.FeedbackDTO;
import com.example.backend.Entities.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackDTO toDTO(Feedback entity);
    Feedback toEntity(FeedbackDTO dto);
}
