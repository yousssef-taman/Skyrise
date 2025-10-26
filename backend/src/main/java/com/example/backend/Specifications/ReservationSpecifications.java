package com.example.backend.Specifications;

import com.example.backend.Constants.EntityAttributes;
import com.example.backend.Entities.Airport;
import com.example.backend.Entities.Flight;
import com.example.backend.Entities.FlightLeg;
import com.example.backend.Entities.Reservation;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ReservationSpecifications {

    public static Specification<Reservation> containsId(String field, Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id);
    }

    public static Specification<Reservation> containsLocation(String field, String location) {
        return (root, query, criteriaBuilder) -> {
            Join<Reservation, Flight> flight = root.join(EntityAttributes.FLIGHT);
            Join<Flight, FlightLeg> flightLeg = flight.join(EntityAttributes.FLIGHT_LEGS);
            Join<FlightLeg, Airport> airport = flightLeg.join(field);

            Subquery<Integer> subquery;
            if (field.equals(EntityAttributes.ARRIVAL_AIRPORT)) {
                subquery = getTheLastFlightLegId(root, query, criteriaBuilder);
            } else {
                subquery = getTheFirstFlightLegId(root, query, criteriaBuilder);
            }


            return criteriaBuilder.and(
                    criteriaBuilder.equal(flightLeg.get(EntityAttributes.FLIGHT_LEG_ID), subquery),
                    criteriaBuilder.equal(airport.get(EntityAttributes.AIRPORT_CITY), location)
            );
        };
    }

    public static Specification<Reservation> containsDate(String field, LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            Join<Reservation, Flight> flightJoin = root.join(EntityAttributes.FLIGHT);
            return criteriaBuilder.equal(flightJoin.get(field), date);
        };
    }

    public static Specification<Reservation> beforeDate(String field, LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            Join<Reservation, Flight> flightJoin = root.join(EntityAttributes.FLIGHT);
            return criteriaBuilder.lessThan(flightJoin.get(field), date);
        };
    }

    public static Specification<Reservation> afterOrEqualDate(String field, LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            Join<Reservation, Flight> flightJoin = root.join(EntityAttributes.FLIGHT);
            return criteriaBuilder.greaterThanOrEqualTo(flightJoin.get(field), date);
        };
    }

    public static Specification<Reservation> sortedByDate(String field, String direction) {
        return (root, query, criteriaBuilder) -> {
            Join<Reservation, Flight> flightJoin = root.join(EntityAttributes.FLIGHT);
            if (direction != null && !direction.isEmpty()) {
                Sort.Direction sortDirection = Sort.Direction.fromString(direction);
                query.orderBy(sortDirection == Sort.Direction.ASC
                        ? criteriaBuilder.asc(flightJoin.get(field))
                        : criteriaBuilder.desc(flightJoin.get(field)));
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Reservation> sortedByFlightId(String direction) {
        return (root, query, criteriaBuilder) -> {
            if (direction != null && !direction.isEmpty()) {
                Sort.Direction sortDirection = Sort.Direction.fromString(direction);
                query.orderBy(sortDirection == Sort.Direction.ASC
                        ? criteriaBuilder.asc(root.get(EntityAttributes.FLIGHT))
                        : criteriaBuilder.desc(root.get(EntityAttributes.FLIGHT)));
            }
            return criteriaBuilder.conjunction();
        };
    }

    private static Subquery<Integer> getTheFirstFlightLegId(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<FlightLeg> subRoot = subquery.from(FlightLeg.class);
        subquery.select(criteriaBuilder.min(subRoot.get(EntityAttributes.FLIGHT_LEG_ID)))
                .where(criteriaBuilder.equal(subRoot.get(EntityAttributes.FLIGHT), root.get(EntityAttributes.FLIGHT)));
        return subquery;
    }

    private static Subquery<Integer> getTheLastFlightLegId(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<FlightLeg> subRoot = subquery.from(FlightLeg.class);
        subquery.select(criteriaBuilder.max(subRoot.get(EntityAttributes.FLIGHT_LEG_ID)))
                .where(criteriaBuilder.equal(subRoot.get(EntityAttributes.FLIGHT), root.get(EntityAttributes.FLIGHT)));
        return subquery;
    }
}