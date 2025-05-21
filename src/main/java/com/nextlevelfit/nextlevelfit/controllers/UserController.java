package com.nextlevelfit.nextlevelfit.controllers;


import com.nextlevelfit.nextlevelfit.exceptions.UserAlreadyExistsException;
import com.nextlevelfit.nextlevelfit.models.User;
import com.nextlevelfit.nextlevelfit.repositories.UserRepository;
import com.nextlevelfit.nextlevelfit.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email,
                                   @RequestParam String password) {
        Optional<User> user = userService.authenticate(email, password);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");

    }

    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(@RequestParam String email,
                                              @RequestParam String username,
                                              @RequestParam String password) {
        try {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);

            userService.createUser(user);
            return ResponseEntity.ok().body("Registration successful!");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
