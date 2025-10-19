package com.example.backend.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.backend.DTOMappers.PassengerMapper;
import com.example.backend.DTOMappers.UserSignUpMapper;
import com.example.backend.DTOs.LogInDTO;
import com.example.backend.DTOs.PassengerDTO;
import com.example.backend.DTOs.UserSignUpDTO;
import com.example.backend.Entities.FlightLeg;
import com.example.backend.Entities.Reservation;
import com.example.backend.Entities.User;
import com.example.backend.Entities.UserCredentials;
import com.example.backend.Enums.Role;
import com.example.backend.Exceptions.AuthenticationException;
import com.example.backend.Exceptions.InvalidPasswordException;
import com.example.backend.Exceptions.UserNotFoundException;
import com.example.backend.Constants.ExceptionMessages;
import com.example.backend.Repositories.ReservationRepository;
import com.example.backend.Repositories.UserCredentialsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.backend.Enums.Gender;
import com.example.backend.Repositories.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSignUpMapper userSignUpMapper;
    private final PassengerMapper passengerMapper;



    public Integer createUser(UserSignUpDTO userSignUpDTO) {
        if (Objects.isNull(userSignUpDTO)) {
            throw new IllegalArgumentException(ExceptionMessages.CUSTOMER_NULL);
        }
        if (!isPasswordValid(userSignUpDTO.userCredentials().getPassword())) {
            throw new InvalidPasswordException(ExceptionMessages.INVALID_PASSWORD);
        }
        userSignUpDTO.userCredentials().setPassword(passwordEncoder.encode(userSignUpDTO.userCredentials().getPassword()));
        User user = userSignUpMapper.toEntity(userSignUpDTO);
        this.userRepository.save(user);
        return user.getUserId();
    }


    public Boolean authenticateUser(String email, String password) {
        UserCredentials userCredentials = this.userCredentialsRepository.findUserCredentialsByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_WITH_EMAIL_NOT_FOUND));
        return passwordEncoder.matches(password, userCredentials.getPassword());
    }


    public void deleteUser(Integer userId) {
        this.userRepository.deleteById(userId);
    }


    public void resetPassword(String email, String password) {
        UserCredentials userUserCredentials = this.userCredentialsRepository.findUserCredentialsByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        if (!isPasswordValid(password)) {
            throw new InvalidPasswordException(ExceptionMessages.INVALID_PASSWORD);
        }
        userUserCredentials.setPassword(passwordEncoder.encode(password));
        this.userCredentialsRepository.save(userUserCredentials);
    }

    private boolean isPasswordValid(String password) {
        return  password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!].*");
    }


    public boolean checkPassword(String email, String password) {
        UserCredentials userUserCredentials = this.userCredentialsRepository.findUserCredentialsByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        return passwordEncoder.matches(password, userUserCredentials.getPassword());
    }


}
