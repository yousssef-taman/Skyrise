package com.example.backend.Services.Reservation.Strategy.SeatAdjustmentStrategy;

import com.example.backend.Entities.Flight;
import org.springframework.stereotype.Component;


@Component("BusinessSeatAdjustment")
public class BusinessSeatAdjustment implements SeatAdjustmentStrategy {



    @Override
    public void addSeats(Flight flight, int reservedSeats) {
        flight.setAvailableBusinessSeats(flight.getAvailableBusinessSeats() + reservedSeats);
    }

    @Override
    public void removeSeats(Flight flight, int reservedSeats) {
        flight.setAvailableBusinessSeats(flight.getAvailableBusinessSeats() - reservedSeats);

    }
}
