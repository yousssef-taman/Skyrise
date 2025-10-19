package com.example.backend.Repositories;

import com.example.backend.Entities.Reservation;
import com.example.backend.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findUserByUserId(Integer id);


    void deleteByAccountAccountId(Integer account_accountId);

    List<User> findByReservation(Reservation reservation);
}
