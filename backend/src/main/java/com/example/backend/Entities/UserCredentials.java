package com.example.backend.Entities;

import com.example.backend.Enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserCredentials {
    @Id
    private Integer id;

    @OneToOne
    @MapsId
    private User user;


    @Column(nullable = false)
    @Enumerated
    private Role role;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


}
