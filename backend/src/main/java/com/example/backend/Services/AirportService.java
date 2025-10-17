package com.example.backend.Services;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.backend.DTOMappers.AirportMapper;
import com.example.backend.DTOs.AirportDTO;
import com.example.backend.Entities.Airport;
import com.example.backend.Repositories.AirportRepository;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airPortMapper;



    public List<AirportDTO> getAllAirports() {
        List<Airport> airports = airportRepository.findAll();
        return airports.stream().map(airPortMapper::toDTO).toList();
    }
}