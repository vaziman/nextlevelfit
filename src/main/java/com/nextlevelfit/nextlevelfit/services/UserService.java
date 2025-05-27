package com.nextlevelfit.nextlevelfit.services;

import com.nextlevelfit.nextlevelfit.exceptions.RoleNotFoundException;
import com.nextlevelfit.nextlevelfit.exceptions.UserAlreadyExistsException;
import com.nextlevelfit.nextlevelfit.models.User;
import com.nextlevelfit.nextlevelfit.repositories.RoleRepository;
import com.nextlevelfit.nextlevelfit.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nextlevelfit.nextlevelfit.models.Role;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class.getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Optional<User> authenticate(String email, String rawPassword) {
        if(email == null || rawPassword == null) {
            log.warning("Authentication failed: email or rawPassword is null");
            return Optional.empty();
        }
        log.info("Attempting to authenticate user: " + email);
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            log.info("User authenticated successful: " + email);
            return Optional.of(user);
        }
        log.warning("User authenticated failed: " + email);
        return Optional.empty();
    }

    @Transactional
    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            log.warning("User already exists: " + user.getEmail());
            throw new UserAlreadyExistsException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRole("USER")
                .orElseThrow(() -> {
                    log.warning("Role not found: " + user.getRole());
                    return new RoleNotFoundException("Role not found");
                });
        user.setRole(role);
        userRepository.save(user);
        log.info("User created: " + user.getEmail());
        return true;
    }
}
