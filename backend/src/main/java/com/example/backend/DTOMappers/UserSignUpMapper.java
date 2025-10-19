package com.example.backend.DTOMappers;


import com.example.backend.DTOs.UserSignUpDTO;
import com.example.backend.Entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSignUpMapper {
    UserSignUpDTO toDTO(User user);
    User toEntity(UserSignUpDTO dto);

}
