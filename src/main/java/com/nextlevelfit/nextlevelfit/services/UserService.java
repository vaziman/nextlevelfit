package com.nextlevelfit.nextlevelfit.services;

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

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Optional<User> authenticate(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists, code 5432");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRole("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
        return true;
    }
}
