package com.nextlevelfit.nextlevelfit.repositories;

import com.nextlevelfit.nextlevelfit.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUser(User user);
}
