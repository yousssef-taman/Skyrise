package com.example.backend.Specifications;

import com.example.backend.Constants.EntityAttributes;
import com.example.backend.Entities.Airport;
import com.example.backend.Entities.Flight;
import com.example.backend.Entities.FlightLeg;
import com.example.backend.Enums.FlightType;
import com.example.backend.Enums.SeatClass;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FlightSpecifications {

    public static Specification<Flight> containsSource(String source) {
        return (root, query, criteriaBuilder) -> {
            Join<Flight, FlightLeg> flightLeg = root.join(EntityAttributes.FLIGHT_LEGS);
            Join<FlightLeg, Airport> airport = flightLeg.join(EntityAttributes.DEPARTURE_AIRPORT);
            return criteriaBuilder.and(criteriaBuilder.equal(flightLeg.get(EntityAttributes.FLIGHT_LEG_ID), getTheFirstFlightLegId(root, query, criteriaBuilder)), criteriaBuilder.equal(airport.get(EntityAttributes.AIRPORT_CITY), source));
        };
    }

    public static Specification<Flight> containsDestination(String destination) {
        return (root, query, criteriaBuilder) -> {
            Join<Flight, FlightLeg> flightLeg = root.join(EntityAttributes.FLIGHT_LEGS);
            Join<FlightLeg, Airport> airport = flightLeg.join(EntityAttributes.ARRIVAL_AIRPORT);
            return criteriaBuilder.and(criteriaBuilder.equal(flightLeg.get(EntityAttributes.FLIGHT_LEG_ID), getTheLastFlightLegId(root, query, criteriaBuilder)), criteriaBuilder.equal(airport.get(EntityAttributes.AIRPORT_CITY), destination));
        };
    }

    public static Specification<Flight> containsStatus(String status) {
        if ("incomplete".equals(status)) return containsStatusIncomplete();
        else if ("complete".equals(status)) return containsStatusComplete();
        else return containsStatusCancel();
    }

    public static Specification<Flight> containsStatusIncomplete() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.greaterThan(root.get(EntityAttributes.AVAILABLE_BUSINESS_SEATS), 0), criteriaBuilder.greaterThan(root.get(EntityAttributes.AVAILABLE_ECONOMY_SEATS), 0));
    }

    public static Specification<Flight> containsStatusComplete() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(EntityAttributes.AVAILABLE_BUSINESS_SEATS), 0), criteriaBuilder.equal(root.get(EntityAttributes.AVAILABLE_ECONOMY_SEATS), 0));
    }


    public static Specification<Flight> containsStatusCancel() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(EntityAttributes.IS_CANCEL), true);
    }


    public static Specification<Flight> hasDepartureDate(LocalDate departureDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(EntityAttributes.DEPARTURE_DATE), departureDate);
    }

    public static Specification<Flight> hasArrivalDateLessThan(LocalDate arrivalDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(EntityAttributes.ARRIVAL_DATE), arrivalDate);
    }

    public static Specification<Flight> hasDepartureAirport(Integer id) {
        return (root, query, criteriaBuilder) -> {
            Join<Flight, FlightLeg> flightLeg = root.join(EntityAttributes.FLIGHT_LEGS);
            Join<FlightLeg, Airport> airport = flightLeg.join(EntityAttributes.DEPARTURE_AIRPORT);

            return criteriaBuilder.and(criteriaBuilder.equal(flightLeg.get(EntityAttributes.FLIGHT_LEG_ID), getTheFirstFlightLegId(root, query, criteriaBuilder)), criteriaBuilder.equal(airport.get(EntityAttributes.ID), id));

        };
    }

    public static Specification<Flight> hasArrivalAirport(Integer id) {
        return (root, query, criteriaBuilder) -> {
            Join<Flight, FlightLeg> flightLeg = root.join(EntityAttributes.FLIGHT_LEGS);
            Join<FlightLeg, Airport> airport = flightLeg.join(EntityAttributes.ARRIVAL_AIRPORT);

            return criteriaBuilder.and(criteriaBuilder.equal(flightLeg.get(EntityAttributes.FLIGHT_LEG_ID), getTheLastFlightLegId(root, query, criteriaBuilder)), criteriaBuilder.equal(airport.get(EntityAttributes.ID), id));

        };
    }

    public static Specification<Flight> hasAvailableSeats(SeatClass seatClass, int numberOfTickets) {
        if (seatClass == SeatClass.ECONOMY) return hasSeats(EntityAttributes.AVAILABLE_ECONOMY_SEATS, numberOfTickets);
        else return hasSeats(EntityAttributes.AVAILABLE_BUSINESS_SEATS, numberOfTickets);
    }

    public static Specification<Flight> hasSeats(String attributeName, int numberOfTickets) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(attributeName), numberOfTickets);
    }

    public static Specification<Flight> hasFlightType(FlightType flightType) {
        if (flightType == FlightType.DIRECT) return haslegs(true);
        else return haslegs(false);
    }

    public static Specification<Flight> haslegs(boolean isDirect) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Long> subquery = getFLightLegCount(root, query, criteriaBuilder, isDirect);
            return criteriaBuilder.exists(subquery);
        };


    }

    private static Subquery<Integer> getTheFirstFlightLegId(Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<FlightLeg> subRoot = subquery.from(FlightLeg.class);
        subquery.select(criteriaBuilder.min(subRoot.get(EntityAttributes.FLIGHT_LEGS))).where(criteriaBuilder.equal(subRoot.get(EntityAttributes.FLIGHT), root));
        return subquery;
    }

    private static Subquery<Integer> getTheLastFlightLegId(Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<FlightLeg> subRoot = subquery.from(FlightLeg.class);

        subquery.select(criteriaBuilder.max(subRoot.get(EntityAttributes.FLIGHT_LEGS))).where(criteriaBuilder.equal(subRoot.get(EntityAttributes.FLIGHT), root));
        return subquery;
    }


    private static Subquery<Long> getFLightLegCount(Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, boolean isDirect) {
        Subquery<Long> subquery = query.subquery(Long.class);

        Root<FlightLeg> subRoot = subquery.from(FlightLeg.class);

        subquery.select(criteriaBuilder.count(subRoot.get(EntityAttributes.FLIGHT_LEG_ID))).where(criteriaBuilder.equal(subRoot.get(EntityAttributes.FLIGHT), root));

        if (isDirect) subquery.having(criteriaBuilder.equal(criteriaBuilder.count(subRoot.get(EntityAttributes.FLIGHT_LEG_ID)), 1));
        else
            subquery.having(criteriaBuilder.greaterThan(criteriaBuilder.count(subRoot.get(EntityAttributes.FLIGHT_LEG_ID)), (long) 1));
        return subquery;
    }
}