package com.nextlevelfit.nextlevelfit.controllers;


import com.nextlevelfit.nextlevelfit.models.Workout;
import com.nextlevelfit.nextlevelfit.repositories.UserRepository;
import com.nextlevelfit.nextlevelfit.repositories.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    @GetMapping
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }


}
