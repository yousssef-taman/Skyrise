package com.example.backend.Services.Reservation.Strategy.AvailableSeatsStrategy;

import com.example.backend.Entities.Flight;

public interface AvailableSeatsStrategy {

    boolean isThereAvailableSeats(Flight flight ,int requiredSeats);
}
