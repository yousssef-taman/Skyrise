package com.example.backend.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.backend.DTOMappers.LogInDTOMapper;
import com.example.backend.DTOMappers.UserMapper;
import com.example.backend.DTOs.LogInDTO;
import com.example.backend.DTOs.PassengerDTO;
import com.example.backend.Entities.FlightLeg;
import com.example.backend.Entities.Reservation;
import com.example.backend.Entities.UserCredentials;
import com.example.backend.Enums.Role;
import com.example.backend.Exceptions.AuthenticationException;
import com.example.backend.Exceptions.InvalidPasswordException;
import com.example.backend.Exceptions.UserNotFoundException;
import com.example.backend.Constants.ExceptionMessages;
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
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final LogInDTOMapper logInDTOMapper;


    @Transactional
    public void signUp(Customer customer) {
        if (Objects.isNull(customer)) {
            throw new IllegalArgumentException(ExceptionMessages.CUSTOMER_NULL);
        }

        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        UserCredentials userCredentials = this.accountServices.createUserAccount(customer.getEmail(), encodedPassword, Role.USER);
        FlightLeg.User user = this.userService.createUser(userCredentials, customer.getCountryCode(), customer.getPhoneNumber(),
                customer.getNationalId(), customer.getDateOfBirth(), customer.getFirstName(), customer.getLastName(),
                customer.getGender(), customer.getPassportNumber(), customer.getPassportIssuingCountry());

    }

    public Integer addAdmin(Admin admin) {
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail(admin.getEmail());
        userCredentials.setPassword(encodedPassword);
        userCredentials.setRole(Role.ADMIN);
        return this.accountServices.addAccount(userCredentials);
    }

    public LogInDTO authenticateUser(String email, String password) {
        UserCredentials userCredentials = this.accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_WITH_EMAIL_NOT_FOUND));
        if (!passwordEncoder.matches(password, userCredentials.getPassword())) {
            throw new AuthenticationException(ExceptionMessages.INVALID_PASSWORD);
        }
        return logInDTOMapper.toDto(userCredentials);
    }

    public LogInDTO signInCheckerByEmail(String email) {

        return this.accountRepository.findAccountByEmail(email).map(logInDTOMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.USER_WITH_EMAIL_NOT_FOUND));

    }

    public ResponseEntity<Integer> signUpCustomer(Customer customer) {
        Integer accountId = this.addCustomer(customer);
        if (accountId != null)
            return new ResponseEntity<>(accountId, HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public FlightLeg.User createUser(UserCredentials userCredentials, String countryCode, String phoneNumber, String nationalId,
                                     LocalDate dateOfBirth,
                                     String firstName, String lastName, Gender gender, String passportNumber,
                                     String passportIssuingCountry) {
        return FlightLeg.User.builder().userCredentials(userCredentials).countryCode(countryCode).phoneNumber(phoneNumber)
                .nationalId(nationalId).dateOfBirth(dateOfBirth).firstName(firstName).lastName(lastName).gender(gender)
                .passportNumber(passportNumber).passportIssuingCountry(passportIssuingCountry).build();
    }

    public Integer addUser(FlightLeg.User user) {
        FlightLeg.User savedUser = this.userRepository.save(user);
        return savedUser.getUserId();
    }

    public void deleteUserByAccountId(Integer accountId) {
        this.userRepository.deleteByAccountAccountId(accountId);
    }

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public void resetPassword(Integer id, String password) {
        UserCredentials userUserCredentials = this.accountRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        if (!isPasswordValid(password)) {
            throw new InvalidPasswordException(ExceptionMessages.INVALID_PASSWORD);
        }
        userUserCredentials.setPassword(passwordEncoder.encode(password));
        this.accountRepository.save(userUserCredentials);
    }

    public void changePassword(String email, String newPassword) {

        UserCredentials userUserCredentials = this.accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        if (!isPasswordValid(newPassword)) {
            throw new InvalidPasswordException(ExceptionMessages.INVALID_PASSWORD);
        }
        userUserCredentials.setPassword(this.passwordEncoder.encode(newPassword));
        accountRepository.save(userUserCredentials);
    }

    private boolean isPasswordValid(String password) {
        return  password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!].*");
    }

    public UserCredentials createUserAccount(String email, String password, Role role) {
        return UserCredentials.builder().email(email).password(passwordEncoder.encode(password)).role(role).build();
    }




    public boolean upgradeUserToAdmin(String email) {
        return this.accountRepository.updateRoleByEmail(email, Role.ADMIN) == 1;
    }

    public boolean deleteAccount(Integer accountId) {
        UserCredentials userUserCredentials = this.accountRepository.findAccountByAccountId(accountId).orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        if (userUserCredentials.getRole() == Role.USER) {
            this.userService.deleteUserByAccountId(accountId);
            this.accountRepository.deleteAccountById(accountId);
        } else if (this.accountRepository.numberOfAdmins(Role.ADMIN) > 1) {
            this.userService.deleteUserByAccountId(accountId);
            this.accountRepository.deleteAccountById(accountId);
        } else {
            throw new IllegalArgumentException(ExceptionMessages.ONLY_ONE_ADMIN);
        }
        return true;
    }

    public boolean checkPassword(Integer accountId, String password) {
        UserCredentials userUserCredentials = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        return passwordEncoder.matches(password, userUserCredentials.getPassword());
    }

    public void addPassengers(List<PassengerDTO> passengersDTOs, Integer userId, Integer flightId) {
        Optional<Reservation> optionalReservation = reservationRepository.findByFlightIdAndUserId(flightId, userId);

        if (optionalReservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found.");
        }
        Reservation reservation = optionalReservation.get();

        List<Passenger> existingPassengers = passengerRepository.findByReservation(reservation);
        if (existingPassengers.size() >= reservation.getReservedSeats()) {
            throw new IllegalArgumentException("Passengers already exist for this reservation. Cannot add more.");
        }

        if (passengersDTOs.size() != reservation.getReservedSeats()) {
            throw new IllegalArgumentException("Number of passengers doesn't match the reserved seats.");
        }

        List<Passenger> passengers = passengersDTOs.stream().map(dto -> UserMapper.toEntity(dto, reservation)).toList();

        passengerRepository.saveAll(passengers);
    }


    public AdminService(AccountServices accountServices) {
        this.accountServices = accountServices  ;
    }

    public boolean upgradeUserToAdmin(String email){
        return  this.accountServices.updateAccountFromCustomerToAdmin(email) ;
    }
}
