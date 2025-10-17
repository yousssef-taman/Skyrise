package com.example.backend.Entities;

import com.example.backend.Entities.CompositeKeys.NotificationPK;
import com.example.backend.Enums.MessageTitle;
import com.example.backend.Enums.Status;
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
public class Notification {

    @EmbeddedId
    private NotificationPK notificationId ;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private MessageTitle title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
