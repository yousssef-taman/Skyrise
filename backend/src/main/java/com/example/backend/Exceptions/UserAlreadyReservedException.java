package com.example.backend.Exceptions;

public class UserAlreadyReservedException extends RuntimeException {
    public UserAlreadyReservedException(Integer userId, Integer flightId) {
        super("User " + userId + " already has a reservation for flight " + flightId);
    }
}
