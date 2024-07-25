package com.Likelion12.fit_mate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExerciseRoutines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "routine_exercises",
            joinColumns = @JoinColumn(name = "routine_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private Set<Exercise> exercises;
}
