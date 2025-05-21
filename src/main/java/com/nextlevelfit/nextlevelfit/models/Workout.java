package com.nextlevelfit.nextlevelfit.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Workout {

    @Id
    private Long id;

    private String name;
    private double distance;
    private int duration;

    @ManyToOne
    User user;

}
