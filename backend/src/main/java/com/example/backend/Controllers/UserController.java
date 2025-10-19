package com.example.backend.Controllers;


import com.example.backend.DTOs.UserSignUpDTO;
import com.example.backend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "signUp")
    public ResponseEntity<Integer> signUp(@RequestBody UserSignUpDTO signUpDTO) {
        return ResponseEntity.ok(userService.createUser(signUpDTO));
    }

    @GetMapping("/logIn/{email}/{password}")
    public ResponseEntity<Void> logIn(@PathVariable String email, @PathVariable String password) {
        Boolean isUserAuthenticated = userService.authenticateUser(email, password);
        if (!isUserAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

}
