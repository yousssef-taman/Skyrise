package com.example.backend.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.backend.Services.FlightLegService;
import com.example.backend.DTOs.FlightLegDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightInfoDisplayController {
    private final FlightLegService flightLegService;

    @GetMapping("/{id}")
    public ResponseEntity<List<FlightLegDTO>> getAllFlightLegs(@PathVariable Integer id) {
        List<FlightLegDTO> listOfFlightLegs = flightLegService.getAllFlightLegs(id);
        return ResponseEntity.ok(listOfFlightLegs);
    }

}
