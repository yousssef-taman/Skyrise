package com.example.backend.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;


public record FlightSearchResponse(
        Integer id,
        LocalDate arrivalDate,
        LocalDate departureDate,
        LocalTime arrivalTime,
        LocalTime departureTime,
        float price) {
}