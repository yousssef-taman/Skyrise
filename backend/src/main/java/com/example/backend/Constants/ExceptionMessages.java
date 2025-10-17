package com.example.backend.Constants;

public final class ExceptionMessages {

    public static final String ACCOUNT_NOT_FOUND = "UserCredentials not found";
    public static final String INVALID_PASSWORD = "New password does not meet the security requirements.";
    public static final String USER_WITH_EMAIL_NOT_FOUND = "User with the provided email does not exist.";
    public static final String ONLY_ONE_ADMIN = "There is only one Admin";
    public static final String CUSTOMER_NULL = "Customer is null";
    public static final String FLIGHT_NOT_FOUND = "Flight not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String NO_CHANGES_MADE = "No changes made";
    public static final String FLIGHT_ALREADY_CANCELLED = "Flight already cancelled";
    public static final String RESERVATION_NOT_FOUND = "Reservation not found";
    public static final String SEAT_CLASS_NOT_FOUND = "Seat class not found";
    public static final String USER_ALREADY_RESERVED_A_SEAT_ON_THIS_FLIGHT = "User already reserved a seat on this flight";
    public static final String AVAILABLE_SEATS_STRATEGY_NOT_FOUND = "Available seats strategy not found";
    public static final String NO_AVAILABLE_SEATS_IN_FLIGHT = "No available seats in the flight";
    private ExceptionMessages() {
    }
}
