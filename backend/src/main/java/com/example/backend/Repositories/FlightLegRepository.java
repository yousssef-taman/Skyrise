package com.example.backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.backend.Entities.FlightLeg;

@Repository
public interface FlightLegRepository extends JpaRepository<FlightLeg, Integer> {
    List<FlightLeg> findByFlightId(Integer flightId);
}
