package com.example.backend.Services;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.DTOs.Admin;
import com.example.backend.DTOs.Customer;
import com.example.backend.DTOs.LogInDTO;
import com.example.backend.Entities.Account;
import com.example.backend.Entities.User;
import com.example.backend.Enums.Role;


@Service
@RequiredArgsConstructor
public class SignUpLogInModuleServices{

    private final UserServices userServices;
    private final AccountServices accountServices;
    private final PasswordEncoder passwordEncoder;


    public Integer addCustomer(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        Account account = this.accountServices.createUserAccount(customer.getEmail(), encodedPassword, Role.USER);
        User user = this.userServices.createUser(account, customer.getCountryCode(), customer.getPhoneNumber(),
                customer.getNationalId(), customer.getDateOfBirth(), customer.getFirstName(), customer.getLastName(),
                customer.getGender(), customer.getPassportNumber(), customer.getPassportIssuingCountry());
        return this.userServices.addUser(user);
    }

    public Integer addAdmin(Admin admin) {
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        Account account = new Account();
        account.setEmail(admin.getEmail());
        account.setPassword(encodedPassword);
        account.setRole(Role.ADMIN);
        return this.accountServices.addAccount(account);
    }

    public ResponseEntity<LogInDTO> signInChecker(String email, String password) {

        Account account = this.accountServices.checkEmailExistence(email);
        if (Objects.equals(account, null) || !passwordEncoder.matches(password, account.getPassword())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LogInDTO id_role = new LogInDTO(account.getAccountId(), account.getRole());
        return new ResponseEntity<>(id_role, HttpStatus.OK);
    }

    public ResponseEntity<LogInDTO> signInCheckerByEmail(String email) {

        Account account = this.accountServices.checkEmailExistence(email);
        LogInDTO id_role = new LogInDTO(account.getAccountId(), account.getRole());
        return new ResponseEntity<>(id_role, HttpStatus.OK);
    }

    public ResponseEntity<Integer> signUpCustomer(Customer customer) {
        Integer accountId = this.addCustomer(customer);
        if (accountId != null)
            return new ResponseEntity<>(accountId, HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
