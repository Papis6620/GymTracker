package com.papis.gymtracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "muscle_groups")
@Getter @Setter
@NoArgsConstructor
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public MuscleGroup(String name) {
        this.name = name;
    }
}
