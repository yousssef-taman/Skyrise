package com.example.backend.Controllers;

import com.example.backend.Services.FlightManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.backend.DTOs.FlightUpdateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class FlightManagementController {
    private final FlightManagementService flightManagementService;

    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable int id) {
        flightManagementService.deleteFlight(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/flights/cancel/{id}")
    public ResponseEntity<Void> cancelFlightAndNotifyPassengers(@PathVariable int id) {
        flightManagementService.cancelFlightAndNotifyPassengers(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/flights/update")
    public ResponseEntity<Void> UpdateFlightAndNotifyPassengers(@Valid @RequestBody FlightUpdateDTO flightDTO) {
        flightManagementService.UpdateFlightAndNotifyPassengers(flightDTO);
        return ResponseEntity.ok().build();
    }
}
