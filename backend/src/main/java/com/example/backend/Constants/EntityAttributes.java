package com.example.backend.Constants;

public final class EntityAttributes {

        private EntityAttributes() {} // prevent instantiation

        // FlightLeg
        public static final String FLIGHT_LEGS = "flightLegs";
        public static final String FLIGHT_LEG_ID = "flightLegId";
        public static final String FLIGHT = "flight";

        // Airport
        public static final String DEPARTURE_AIRPORT = "departureAirport";
        public static final String ARRIVAL_AIRPORT = "arrivalAirport";
        public static final String AIRPORT_CITY = "airportCity";
        public static final String ID = "id";

        // Flight
        public static final String AVAILABLE_BUSINESS_SEATS = "availableBusinessSeats";
        public static final String AVAILABLE_ECONOMY_SEATS = "availableEconomySeats";
        public static final String IS_CANCEL = "isCancel";
        public static final String DEPARTURE_DATE = "departureDate";
        public static final String ARRIVAL_DATE = "arrivalDate";
    }