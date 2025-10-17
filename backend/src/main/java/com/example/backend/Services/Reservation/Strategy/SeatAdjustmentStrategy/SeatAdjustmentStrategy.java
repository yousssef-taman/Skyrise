package com.example.backend.Services.Reservation.Strategy.SeatAdjustmentStrategy;

import com.example.backend.Entities.Flight;

public interface SeatAdjustmentStrategy {
    void addSeats(Flight flight, int reservedSeats);
    void removeSeats(Flight flight, int reservedSeats);
}
