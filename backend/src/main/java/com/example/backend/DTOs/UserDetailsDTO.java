package com.example.backend.DTOs;

import com.example.backend.Enums.Gender;
import java.time.LocalDate;

public record UserDetailsDTO(
    String countryCode,
    String phoneNumber,
    String nationalId,
    LocalDate dateOfBirth,
    String firstName,
    String lastName,
    Gender gender,
    String passportNumber,
    String passportIssuingCountry,
    String email,
    String password
) {}
