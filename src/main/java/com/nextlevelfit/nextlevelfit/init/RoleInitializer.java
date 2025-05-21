package com.nextlevelfit.nextlevelfit.init;


import com.nextlevelfit.nextlevelfit.models.Role;
import com.nextlevelfit.nextlevelfit.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findByRole("USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setRole("USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByRole("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRole("ADMIN");
            roleRepository.save(adminRole);
        }
    }
}
