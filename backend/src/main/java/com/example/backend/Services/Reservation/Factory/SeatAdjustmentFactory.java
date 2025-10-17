package com.example.backend.Services.Reservation.Factory;


import com.example.backend.Constants.ExceptionMessages;
import com.example.backend.Enums.SeatClass;
import com.example.backend.Services.Reservation.Strategy.SeatAdjustmentStrategy.SeatAdjustmentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SeatAdjustmentFactory {


    private final Map<String, SeatAdjustmentStrategy> seatAdjustmentStrategies;

    public SeatAdjustmentStrategy getStrategyFor(SeatClass seatClass) {
        SeatAdjustmentStrategy seatAdjustmentStrategy = seatAdjustmentStrategies.get(seatClass.toString());
        if (seatAdjustmentStrategy == null) {
            throw new IllegalArgumentException(ExceptionMessages.SEAT_CLASS_NOT_FOUND);
        }
        return seatAdjustmentStrategy;
    }
}
