package com.example.backend.Constants;

public class LogMessages {
    private LogMessages() {}
    public static final String RESERVATION_CANCEL_START = "Attempting to cancel reservation for user {} on flight {}";
    public static final String RESERVATION_CANCEL_SUCCESS = "Successfully cancelled reservation for user {} on flight {}";
    public static final String RESERVATION_NOT_FOUND = "Reservation not found for user {} and flight {}";
    public static final String RESERVATION_CREATION_SUCCESS = "Successfully created reservation for user {} on flight {}";
}
