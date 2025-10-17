package com.example.backend.Entities;

import com.example.backend.Entities.CompositeKeys.FlightLegPK;

import com.example.backend.Enums.Gender;
import com.example.backend.Enums.MealSpecification;
import com.example.backend.Enums.SpecialNeeds;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FlightLeg {

    @EmbeddedId
    private FlightLegPK flightLegId;

    @ManyToOne
    @MapsId("flightId")
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;

    @Column(nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false)
    private LocalTime departureTime;
}
