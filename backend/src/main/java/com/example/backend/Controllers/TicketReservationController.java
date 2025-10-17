package com.example.backend.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.backend.DTOs.TicketDTO;
import com.example.backend.Services.Reservation.ReservationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/user")
public class TicketReservationController {

    private final ReservationService reservationService;

    public TicketReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<String> addReservation(@Valid @RequestBody TicketDTO ticketDTO) {
        reservationService.createReservation(ticketDTO);

        return ResponseEntity.ok("Booking tickets is done successfully.");
    }

    @DeleteMapping("/reservations/{flightId}/{userId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Integer flightId, @PathVariable Integer userId) {
        reservationService.cancelReservation(flightId, userId);
        return ResponseEntity.ok("Deleting tickets is done successfully.");
    }

}
