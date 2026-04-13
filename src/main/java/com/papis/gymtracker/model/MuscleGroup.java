package com.papis.gymtracker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "muscle_groups")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
