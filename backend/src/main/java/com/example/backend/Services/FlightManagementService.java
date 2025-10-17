package com.example.backend.Services;
import com.example.backend.Constants.ExceptionMessages;
import com.example.backend.DTOMappers.FlightLegMapper;
import com.example.backend.DTOs.FlightLegDTO;
import com.example.backend.DTOs.FlightLegUpdateDTO;
import com.example.backend.DTOs.FlightUpdateDTO;
import com.example.backend.Entities.Flight;
import com.example.backend.Entities.Reservation;
import com.example.backend.Entities.User;
import com.example.backend.Enums.FlightUpdate;
import com.example.backend.Repositories.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FlightManagementService {

    private final FlightRepository flightRepository;
    private final NotificationService notificationService;
    private final FlightLegMapper flightLegMapper;



    public List<User> extractPassengersFromFlight(Flight flight) {
        return flight.getReservations().stream().map(Reservation::getUser).toList();
    }

    public void deleteFlight(int id) {
        flightRepository.deleteById(id);
    }

    @Transactional
    public void cancelFlightAndNotifyPassengers(int flightId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.FLIGHT_NOT_FOUND));
        cancelFlight(flight);
        notificationService.notifyPassengersOfFlightUpdate(flight, FlightUpdate.CANCELLED);
    }

    @Transactional
    public void UpdateFlightAndNotifyPassengers(FlightUpdateDTO flightDTO) {
        Flight flight = flightRepository.findById(flightDTO.id()).orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.FLIGHT_NOT_FOUND));
        updateFlight(flight, flightDTO);
        notificationService.notifyPassengersOfFlightUpdate(flight, FlightUpdate.UPDATED);

    }


    public void cancelFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException(ExceptionMessages.FLIGHT_NOT_FOUND);
        }
        if (flight.isCanceled()) {
            throw new IllegalArgumentException(ExceptionMessages.FLIGHT_ALREADY_CANCELLED);
        }
        flight.setCanceled(true);
        flightRepository.save(flight);
    }


    public void updateFlight(Flight flight, FlightUpdateDTO updates) {
        if (flight == null) {
            throw new IllegalArgumentException(ExceptionMessages.FLIGHT_NOT_FOUND);
        }
        if (flight.isCanceled()) {
            throw new IllegalArgumentException("Cannot update a cancelled flight.");
        }
        if (!(hasDateChanged(flight, updates) || haveFlightLegsChanged(flight, updates.flightLegs()))) {
            throw new IllegalArgumentException(ExceptionMessages.NO_CHANGES_MADE);
        }
        flightRepository.save(flight);
    }


    private boolean hasDateChanged(Flight flight, FlightUpdateDTO updates) {
        return !Objects.equals(flight.getDepartureDate(), updates.departureDate())
                || !Objects.equals(flight.getArrivalDate(), updates.arrivalDate());
    }

    private boolean haveFlightLegsChanged(Flight flight, List<FlightLegUpdateDTO> updatedLegs) {
        if (updatedLegs == null || flight.getFlightLegs() == null) return false;
        List<FlightLegDTO> flightLegDTOS = flight.getFlightLegs().stream().map(flightLegMapper::toDTO).toList();
        return flightLegDTOS.equals(updatedLegs);
    }
}
