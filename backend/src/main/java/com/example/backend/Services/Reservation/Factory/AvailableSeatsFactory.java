package com.example.backend.Services.Reservation.Factory;

import com.example.backend.Constants.ExceptionMessages;
import com.example.backend.Enums.SeatClass;
import com.example.backend.Services.Reservation.Strategy.AvailableSeatsStrategy.AvailableSeatsStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AvailableSeatsFactory {

    private final Map<String, AvailableSeatsStrategy> availableSeatsStrategies;

    public AvailableSeatsStrategy getStrategyFor(SeatClass seatClass) {
        AvailableSeatsStrategy availableSeatsStrategy = availableSeatsStrategies.get(seatClass.toString());
        if (availableSeatsStrategy == null) {
            throw new IllegalArgumentException(ExceptionMessages.AVAILABLE_SEATS_STRATEGY_NOT_FOUND);
        }
        return availableSeatsStrategy;
    }





}
