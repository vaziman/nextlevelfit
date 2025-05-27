package com.nextlevelfit.nextlevelfit;

import com.nextlevelfit.nextlevelfit.exceptions.RoleNotFoundException;
import com.nextlevelfit.nextlevelfit.exceptions.UserAlreadyExistsException;
import com.nextlevelfit.nextlevelfit.models.Role;
import com.nextlevelfit.nextlevelfit.models.User;
import com.nextlevelfit.nextlevelfit.repositories.RoleRepository;
import com.nextlevelfit.nextlevelfit.repositories.UserRepository;
import com.nextlevelfit.nextlevelfit.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    // Tests for authenticate()
    @Test
    void authenticate_shouldReturnUser_whenPasswordIsCorrect() {
        String email = "text@example.com";
        String rawPassword = "password";
        String hashedPassword = "$ajs$ssk$hashed";

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(true);

        Optional<User> result = userService.authenticate(email, rawPassword);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());

    }

    @Test
    void authenticate_shouldReturnEmpty_whenUserNotFound() {

        when(userRepository.findByEmail("none@example.com")).thenReturn(null);
        Optional<User> result = userService.authenticate("none@example.com", "pass");
        assertTrue(result.isEmpty());
    }

    @Test
    void authenticate_shouldReturnEmpty_whenPasswordIsIncorrect() {

        String email = "text@example.com";
        String rawPassword = "wrongPassword";
        String correctHashedPassword = "sk$hashed";

        User user = new User();
        user.setEmail(email);
        user.setPassword(correctHashedPassword);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, correctHashedPassword)).thenReturn(false);

        Optional<User> result = userService.authenticate(email, rawPassword);
        assertTrue(result.isEmpty());
    }

    @Test
    void authenticate_shouldReturnEmpty_whenPasswordIsNull() {
        String email = "text@example.com";
        String rawPassword = null;

        Optional<User> result = userService.authenticate(email, rawPassword);
        assertTrue(result.isEmpty());
    }

    @Test
    void authenticate_shouldReturnEmpty_whenEmailIsNull() {
        String email = null;
        String rawPassword = "password";

        Optional<User> result = userService.authenticate(email, rawPassword);
        assertTrue(result.isEmpty());
    }

    //tests for createUser()
    @Test
    void createUser_shouldReturnTrue_whenInputIsValid() {
        String email = "text@example.com";
        String rawPassword = "password";
        String hashedPassword = "$ajs$ssk$hashed";

        Role role = new Role();
        role.setRole("USER");

        User user = new User();
        user.setEmail(email);
        user.setPassword(rawPassword);


        when(userRepository.findByEmail(email)).thenReturn(null);
        when(passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);
        when(roleRepository.findByRole("USER")).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean result = userService.createUser(user);
        assertTrue(result);
        assertEquals(hashedPassword, user.getPassword());
        assertEquals(role, user.getRole());

        verify(userRepository).save(user);

    }

    @Test
    void createUser_shouldThrowUserAlreadyExistsException_whenEmailAlreadyExists() {
        String email = "text@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("User already exists", ex.getMessage());
        verify(userRepository, never()).save(any());

    }

    @Test
    void createUser_shouldThrowRoleNotFoundException_whenRoleNotFound() {
        String email = "text@example.com";
        String rawPassword = "password";
        String hashedPassword = "$ajs$ssk$hashed";

        User user = new User();
        user.setEmail(email);
        user.setPassword(rawPassword);

        when(userRepository.findByEmail(email)).thenReturn(null);
        when(passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);
        when(roleRepository.findByRole("USER")).thenReturn(Optional.empty());

        RoleNotFoundException ex = assertThrows(RoleNotFoundException.class, () -> {
            userService.createUser(user);
        });
        assertEquals("Role not found", ex.getMessage());
    }


}
