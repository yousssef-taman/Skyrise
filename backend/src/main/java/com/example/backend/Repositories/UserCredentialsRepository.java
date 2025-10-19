package com.example.backend.Repositories;

import com.example.backend.Entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {

    Optional<UserCredentials> findUserCredentialsByEmail(String email);
}
