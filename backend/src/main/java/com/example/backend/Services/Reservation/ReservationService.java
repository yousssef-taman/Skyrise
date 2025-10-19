package com.example.backend.Services.Reservation;

import com.example.backend.Constants.ExceptionMessages;
import com.example.backend.Constants.LogMessages;
import com.example.backend.DTOMappers.PassengerMapper;
import com.example.backend.DTOs.PassengerDTO;
import com.example.backend.DTOs.TicketDTO;
import com.example.backend.Entities.Flight;
import com.example.backend.Entities.Reservation;
import com.example.backend.Entities.User;
import com.example.backend.Exceptions.NoAvailableSeatsException;
import com.example.backend.Exceptions.UserAlreadyReservedException;
import com.example.backend.Repositories.FlightRepository;
import com.example.backend.Repositories.ReservationRepository;
import com.example.backend.Repositories.UserRepository;
import com.example.backend.Services.Reservation.Factory.AvailableSeatsFactory;
import com.example.backend.Services.Reservation.Factory.SeatAdjustmentFactory;
import com.example.backend.Services.Reservation.Strategy.AvailableSeatsStrategy.AvailableSeatsStrategy;
import com.example.backend.Services.Reservation.Strategy.SeatAdjustmentStrategy.SeatAdjustmentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final SeatAdjustmentFactory seatAdjustmentFactory;
    private final AvailableSeatsFactory availableSeatsFactory;
    private final PassengerMapper passengerMapper;

    public void createReservation(TicketDTO ticketDTO) {


        Flight flight = flightRepository.findById(ticketDTO.flightId())
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.FLIGHT_NOT_FOUND));

        AvailableSeatsStrategy availableSeats = availableSeatsFactory.getStrategyFor(ticketDTO.seatClass());
        SeatAdjustmentStrategy adjustSeats = seatAdjustmentFactory.getStrategyFor(ticketDTO.seatClass());

        if (userHasOtherReservationsInTheSameFlight(ticketDTO)) {
            throw new UserAlreadyReservedException(ticketDTO.userId(), ticketDTO.flightId());
        }

        if (!availableSeats.isThereAvailableSeats(flight, ticketDTO.reservedSeats())) {
            throw new NoAvailableSeatsException(ExceptionMessages.NO_AVAILABLE_SEATS_IN_FLIGHT + ticketDTO.flightId());
        }

        adjustSeats.removeSeats(flight, ticketDTO.reservedSeats());
        saveReservation(ticketDTO, flight);
        log.info(LogMessages.RESERVATION_CREATION_SUCCESS, ticketDTO.userId(), ticketDTO.flightId());
    }

    public void cancelReservation(Integer flightId, Integer userId) {

        Reservation reservation = reservationRepository.findByFlightIdAndUserId(flightId, userId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.RESERVATION_NOT_FOUND));
        Flight flight = reservation.getFlight();

        SeatAdjustmentStrategy adjustSeats = seatAdjustmentFactory.getStrategyFor(reservation.getSeatClass());

        adjustSeats.addSeats(flight, reservation.getReservedSeats());

        flight.removeReservation(reservation);
        flightRepository.save(flight);
        log.info(LogMessages.RESERVATION_CANCEL_SUCCESS, userId, flightId);
    }

    private boolean userHasOtherReservationsInTheSameFlight(TicketDTO ticket) {
        return reservationRepository.existsByUserIdAndFlightId(ticket.userId(), ticket.flightId());
    }

    private void saveReservation(TicketDTO ticket, Flight flight) {
        User passenger = userRepository.findById(ticket.userId())
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));
        Reservation reservation = Reservation.builder().user(passenger).flight(flight).seatClass(ticket.seatClass()).reservedSeats(ticket.reservedSeats()).build();
        reservationRepository.save(reservation);
    }

    public void addPassengersToReservation(List<PassengerDTO> passengerDTOs, Integer userId, Integer flightId) {
        Reservation reservation = reservationRepository.findByFlightIdAndUserId(flightId, userId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.RESERVATION_NOT_FOUND));

        validatePassengerCountMatchesReservedSeats(reservation, passengerDTOs.size());

        List<User> passengers = passengerDTOs.stream()
                .map(passengerMapper::toEntity).toList();
        userRepository.saveAll(passengers);
    }

    private void validatePassengerCountMatchesReservedSeats(Reservation reservation, int numberOfPassengers) {
        List<User> reservedPassengers = userRepository.findByReservation(reservation);
        int reservedSeats = reservation.getReservedSeats();
        boolean haveSeatsBeenReservedBefore = !reservedPassengers.isEmpty();
        if (haveSeatsBeenReservedBefore) {
            throw new IllegalArgumentException(ExceptionMessages.SEATS_HAVE_BEEN_RESERVED_BEFORE);
        }
        boolean isNumberOfPassengersMatchesTheReservedSeats = numberOfPassengers == reservedSeats;
        if (!isNumberOfPassengersMatchesTheReservedSeats) {
            throw new IllegalArgumentException(ExceptionMessages.PASSENGER_COUNT_MISMATCH);
        }
    }
}
