package com.example.backend.DTOs;

import com.example.backend.Entities.UserCredentials;
import com.example.backend.Enums.Gender;
import com.example.backend.Enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserSignUpDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotNull
        UserCredentials userCredentials,
        @NotBlank
        String passportNumber,
        @NotBlank
        String passportIssuingCountry,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String countryCode,
        @NotBlank
        String nationalId,
        @NotBlank
        LocalDate dateOfBirth,
        @NotBlank
        Gender gender,
        Role role
) {
}
