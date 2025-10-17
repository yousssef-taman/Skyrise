package com.example.backend.Entities.CompositeKeys;
import java.io.Serializable;

import jakarta.persistence.Embeddable;
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
public class NotificationPK implements Serializable {
    private Integer notificationId;
    private Integer userId;
}