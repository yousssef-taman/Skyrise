package com.example.backend.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.backend.DTOs.AirportDTO;
import com.example.backend.Services.AirportService;


@Controller
public class AirportDisplayController {

    private final AirportService airportService;

    public AirportDisplayController (AirportService airportService) {
        this.airportService = airportService;
    }
    
    @GetMapping("/airports")
    public ResponseEntity<List<AirportDTO>> getAllAirports() {

        List<AirportDTO> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }
    

}