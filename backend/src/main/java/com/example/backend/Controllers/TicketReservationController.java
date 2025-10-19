package com.example.backend.Controllers;

import com.example.backend.DTOs.PassengerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.backend.DTOs.TicketDTO;
import com.example.backend.Services.Reservation.ReservationService;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/user")
public class TicketReservationController {

    private final ReservationService reservationService;

    public TicketReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> addReservation(@Valid @RequestBody TicketDTO ticketDTO) {
        reservationService.createReservation(ticketDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservations/{flightId}/{userId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer flightId, @PathVariable Integer userId) {
        reservationService.cancelReservation(flightId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/passengers")
    public ResponseEntity<Void> addPassengers(@RequestBody List<@Valid PassengerDTO> passengers,
                                                @RequestParam Integer userId,
                                                @RequestParam Integer flightId) {

        reservationService.addPassengersToReservation(passengers, userId, flightId);

        return ResponseEntity.ok().build();
    }

}
