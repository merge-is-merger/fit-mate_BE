package com.Likelion12.fit_mate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Challenge")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    // Getters and Setters
}
