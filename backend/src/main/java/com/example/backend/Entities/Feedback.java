package com.example.backend.Entities;

import java.time.LocalDateTime;
import com.example.backend.Enums.QualityRating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("flightId")
    @JoinColumn(name = "flight_id")
    private Flight flight;

    private String comment;

    @Column(nullable = false)
    LocalDateTime dateOfCreation;

    @Column(nullable = false)
    short stars;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    QualityRating punctuality;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    QualityRating comfort;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    QualityRating service;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    QualityRating foodAndBeverage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    QualityRating cleanliness;
}
