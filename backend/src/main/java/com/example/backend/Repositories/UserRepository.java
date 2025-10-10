package com.example.backend.Repositories;

import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.backend.Entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findUserByUserId(Integer id);


    void deleteByAccountAccountId(Integer account_accountId);
}
