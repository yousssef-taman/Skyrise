package com.example.backend.Controllers;

import com.example.backend.DTOs.LogInDTO;
import com.example.backend.DTOs.PassengerDTO;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class UserController {


    @PostMapping("/passengers")
    public ResponseEntity<String> addPassengers(@RequestBody List<@Valid PassengerDTO> passengers,
                                                @RequestParam Integer userId,
                                                @RequestParam Integer flightId) {

        passengerService.addPassengers(passengers, userId, flightId);

        return ResponseEntity.ok("Passenger added successfully.");
    }

    @PutMapping(path = "upgrade")
    public ResponseEntity<String> upgradeUser(@RequestParam String email) {
        boolean flag = adminService.upgradeUserToAdmin(email);
        if (flag) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed", HttpStatus.NOT_FOUND);
    }


    @PostMapping(path = "signUp/customer")
    public ResponseEntity<Integer> signUp(@RequestBody Customer customer) {
        return this.signUpLogInModuleServices.signUpCustomer(customer);
    }

    @GetMapping("logIn")
    public ResponseEntity<LogInDTO> logIn(@PathParam(value = "email") String email, @PathParam(value = "password") String password) {
        return this.signUpLogInModuleServices.signInChecker(email, password);
    }

}
