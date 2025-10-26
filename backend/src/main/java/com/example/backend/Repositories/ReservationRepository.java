package com.example.backend.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.backend.Entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>, JpaSpecificationExecutor<Reservation> {
    
    boolean existsByUserIdAndFlightId(Integer userId, Integer flightId);

    Optional<Reservation> findByFlightIdAndUserId(Integer flightId, Integer userId);
}
