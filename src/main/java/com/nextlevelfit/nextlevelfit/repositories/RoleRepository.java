package com.nextlevelfit.nextlevelfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nextlevelfit.nextlevelfit.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String name);

}
