package com.example.backend.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.DTOMappers.FlightLegMapper;
import com.example.backend.DTOs.FlightLegDTO;
import com.example.backend.Entities.FlightLeg;
import com.example.backend.Repositories.FlightLegRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FlightLegService {
    private final FlightLegRepository flightLegRepository;
    private final FlightLegMapper flightLegMapper;

    public List<FlightLegDTO> getAllFlightLegs(Integer flightId) {
        List<FlightLeg> listOfFlightLeg = flightLegRepository.findByFlightId(flightId);
        return listOfFlightLeg.stream().map(flightLegMapper::toDTO).toList();
    }

}
