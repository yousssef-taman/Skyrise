package com.example.backend.Services;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.Entities.*;
import com.example.backend.Enums.FlightUpdate;
import com.example.backend.Enums.MessageTitle;
import com.example.backend.Enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.DTOMappers.NotificationMapper;
import com.example.backend.DTOs.NotificationDTO;
import com.example.backend.Repositories.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final int PAGE_SIZE = 10;

    private final NotificationRepository notificationRepository;
    private final FlightManagementService flightManagementService;
    private final NotificationMapper notificationMapper;

    public Page<NotificationDTO> getNotification(Integer userId,Pageable pageable) {
        return notificationRepository.findByUserId(userId, pageable)
                                        .map(notificationMapper::toDTO);
    }

    public void addNotificationToConcernedUsers(List<User> passengers, Notification notification) {
        passengers.forEach(passenger -> {
            passenger.addNotification(notification);
            });
    }

    public void notifyPassengersOfFlightUpdate(Flight flight , FlightUpdate flightUpdate) {
        List<User> passengers = flightManagementService.extractPassengersFromFlight(flight);
        if (passengers.isEmpty()) return ;
        Notification flightCancellationNotification = buildNotificationFromFlight(flight , flightUpdate);
        addNotificationToConcernedUsers(passengers, flightCancellationNotification);
        }

    private Notification buildNotificationFromFlight(Flight flight, FlightUpdate flightUpdate) {
        FlightLeg firstLeg = flight.getFlightLegs().get(0);
        FlightLeg lastLeg = flight.getFlightLegs().get(flight.getFlightLegs().size() - 1);

        String source = firstLeg.getDepartureAirport().getAirportCity();
        String destination = lastLeg.getArrivalAirport().getAirportCity();
        String message = buildNotificationMessage(flight.getDepartureDate(), source, destination, flightUpdate);
        return Notification.builder()
                .title(MessageTitle.CANCELLED)
                .message(message)
                .status(Status.UNSEEN)
                .flight(flight)
                .build();
    }


    private String buildNotificationMessage(LocalDate departureDate, String source, String destination, FlightUpdate flightUpdate) {
        return "Flight with departure date: " + departureDate +
                " , source: " + source +
                " , and destination: " + destination +
                " has been " + flightUpdate.toString().toLowerCase();
    }
}
