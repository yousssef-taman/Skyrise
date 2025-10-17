package com.example.backend.Entities;


import com.example.backend.Entities.CompositeKeys.PaymentPK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @EmbeddedId
    private PaymentPK paymentId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private FlightLeg.User user;

    @ManyToOne
    @MapsId("flightId")
    @JoinColumn(name = "flightId")
    private Flight flight;


    @Column
    private String creditCardNumber ;

    @Column
    private Double amount ;

}
