package com.example.backend.Services.Reservation.Strategy.AvailableSeatsStrategy;

import com.example.backend.Entities.Flight;
import org.springframework.stereotype.Component;


@Component("ECONOMY")
public class EconomyAvailableSetas implements AvailableSeatsStrategy{
    @Override
    public boolean isThereAvailableSeats(Flight flight, int requiredSeats) {
        return flight.getAvailableEconomySeats() >= requiredSeats;
    }
}
