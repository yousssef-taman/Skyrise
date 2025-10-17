package com.example.backend.Entities;

import java.util.List;
import com.example.backend.Entities.CompositeKeys.ReservationPK;
import com.example.backend.Enums.SeatClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @EmbeddedId
    private ReservationPK reservationId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("flightId")
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatClass seatClass;

    @Column(nullable = false)
    private int reservedSeats;

    @OneToMany(mappedBy = "reservation")
    private List<User> passengers;
}
