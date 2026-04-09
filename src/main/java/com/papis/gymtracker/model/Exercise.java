package com.papis.gymtracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "exercises")
@Getter @Setter
@NoArgsConstructor
public class Exercise {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "force_type")
    @Enumerated(EnumType.STRING)
    private String force;

    @Enumerated(EnumType.STRING)
    private String level;

    @Enumerated(EnumType.STRING)
    private String mechanic;

    @Enumerated(EnumType.STRING)
    private String equipment;

    @Enumerated(EnumType.STRING)
    private String category;

    @ManyToMany
    @JoinTable(
            name = "exercise_primary_muscles",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private Set<MuscleGroup> primaryMuscles;

    @ManyToMany
    @JoinTable(
            name = "exercise_secondary_muscles",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private Set<MuscleGroup> secondaryMuscles;

}
