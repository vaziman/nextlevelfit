package com.nextlevelfit.nextlevelfit.repositories;

import com.nextlevelfit.nextlevelfit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
