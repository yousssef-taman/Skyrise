package com.example.backend.Services;

import com.example.backend.Entities.UserCredentials;
import com.example.backend.Enums.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
@Transactional
public class UserCredentialsServicesTest {

    @Autowired
    private AccountServices accountServices;
    @Autowired
    private AccountRepository accountRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    void passwordChange() {
        UserCredentials userCredentials = accountServices.createAccount("email1", "Password123!", Role.USER);
        accountServices.addAccount(userCredentials);

        accountServices.changePassword(userCredentials.getEmail(), "Password12!");
        Assertions.assertEquals(userCredentials.getPassword() == "Password123!", false);
    }

    @Test
    void passwordReset() {
        UserCredentials userCredentials = accountServices.createAccount("email1", "Password123!", Role.USER);
        accountServices.addAccount(userCredentials);

        accountServices.resetPassword(userCredentials.getAccountId(), "Password12!");
        Assertions.assertEquals(userCredentials.getPassword() == "Password123!", false);
    }

    @Test
    void deleteAccount() {
        UserCredentials userCredentials = accountServices.createAccount("email1", "Password123!", Role.USER);
        accountServices.addAccount(userCredentials);
        Assertions.assertEquals(accountRepository.findAccountByAccountId(userCredentials.getAccountId()).isEmpty(), false);

        accountServices.deleteAccount(userCredentials.getAccountId());
        Assertions.assertEquals(accountRepository.findAccountByAccountId(userCredentials.getAccountId()).isEmpty(), true);

        UserCredentials userCredentials2 = accountServices.createAccount("email1", "Password123!", Role.ADMIN);
        accountServices.addAccount(userCredentials2);

        try{
            accountServices.deleteAccount(userCredentials2.getAccountId());
            Assertions.assertEquals(accountRepository.findAccountByAccountId(userCredentials2.getAccountId()).isEmpty(), true);
        }
        catch (Exception e){
            Assertions.assertEquals(e.getMessage(), "There is only one Admin");
        }

        UserCredentials userCredentials3 = accountServices.createAccount("email12", "Password123!", Role.ADMIN);
        accountServices.addAccount(userCredentials3);

        try{
            accountServices.deleteAccount(userCredentials2.getAccountId());
            Assertions.assertEquals(accountRepository.findAccountByAccountId(userCredentials2.getAccountId()).isEmpty(), true);
        }
        catch (Exception e){
            Assertions.assertEquals(e.getMessage(), "There is only one Admin");
        }


    }


}


