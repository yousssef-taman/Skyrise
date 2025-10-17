package com.example.backend.Services.Reservation.Strategy.AvailableSeatsStrategy;

import com.example.backend.Entities.Flight;
import org.springframework.stereotype.Component;

@Component("BUSINESS")
public class BusinessAvailableSeats implements  AvailableSeatsStrategy {
    @Override
    public boolean isThereAvailableSeats(Flight flight, int requiredSeats) {
        return flight.getAvailableBusinessSeats() >= requiredSeats;
    }
}
