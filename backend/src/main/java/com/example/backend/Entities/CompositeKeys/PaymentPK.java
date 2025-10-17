package com.example.backend.Entities.CompositeKeys;

import java.io.Serializable;


import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@ToString
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentPK implements Serializable{

    private Integer userId;
    private Integer flightId;
}
