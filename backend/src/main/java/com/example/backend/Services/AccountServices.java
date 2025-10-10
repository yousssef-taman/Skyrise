package com.example.backend.Services;

import com.example.backend.Entities.Account;
import com.example.backend.Enums.Role;
import com.example.backend.Exceptions.InvalidPasswordException;
import com.example.backend.Exceptions.UserNotFoundException;
import com.example.backend.Repositories.AccountRepository;
import com.example.backend.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServices {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServices userServices;

    public void resetPassword(Integer id, String password) {
        Account userAccount = this.accountRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        if (!isPasswordValid(password)) {
            throw new InvalidPasswordException(ExceptionMessages.INVALID_PASSWORD);
        }
        userAccount.setPassword(passwordEncoder.encode(password));
        this.accountRepository.save(userAccount);
    }

    public void changePassword(String email, String newPassword) {

        Account userAccount = this.accountRepository.findAccountByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        if (!isPasswordValid(newPassword)) {
            throw new InvalidPasswordException(ExceptionMessages.INVALID_PASSWORD);
        }
        userAccount.setPassword(this.passwordEncoder.encode(newPassword));
        accountRepository.save(userAccount);
    }

    private boolean isPasswordValid(String password) {
        return  password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!].*");
    }

    public Account createUserAccount(String email, String password, Role role) {
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setRole(role);
        return account;
    }

    public Integer addAccount(Account account) {
        try {
            this.accountRepository.save(account);
            return account.getAccountId();
        } catch (Exception e) {
            return null;
        }
    }

    public Account checkEmailExistence(String email) {
        Optional<Account> optionalAccount = this.accountRepository.findAccountByEmail(email);
        return optionalAccount.orElse(null);
    }

    public boolean updateAccountFromCustomerToAdmin(String email) {
        int flag = this.accountRepository.updateRoleByEmail(email, Role.ADMIN);
        return flag == 1;
    }

    public boolean deleteAccount(Integer accountId) {
        Account userAccount = this.accountRepository.findAccountByAccountId(accountId).orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        if (userAccount.getRole() == Role.USER) {
            this.userServices.deleteUserByAccountId(accountId);
            this.accountRepository.deleteAccountById(accountId);
        } else if (this.accountRepository.numberOfAdmins(Role.ADMIN) > 1) {
            this.userServices.deleteUserByAccountId(accountId);
            this.accountRepository.deleteAccountById(accountId);
        } else {
            throw new IllegalArgumentException(ExceptionMessages.ONLY_ONE_ADMIN);
        }
        return true;
    }

    public boolean checkPassword(Integer accountId, String password) {
        Account userAccount = this.accountRepository.findById(accountId)
            .orElseThrow(() -> new UserNotFoundException(ExceptionMessages.ACCOUNT_NOT_FOUND));
        return passwordEncoder.matches(password, userAccount.getPassword());
    }

}