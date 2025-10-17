package com.example.backend.Services.Reservation.Strategy.SeatAdjustmentStrategy;

import com.example.backend.Entities.Flight;
import org.springframework.stereotype.Component;

@Component("ECONOMY")
public class EconomySeatAdjustment implements SeatAdjustmentStrategy{
    @Override
    public void addSeats(Flight flight, int reservedSeats) {
        flight.setAvailableEconomySeats(flight.getAvailableEconomySeats() + reservedSeats);
    }

    @Override
    public void removeSeats(Flight flight, int reservedSeats) {
        flight.setAvailableBusinessSeats(flight.getAvailableBusinessSeats() - reservedSeats);
    }
}
