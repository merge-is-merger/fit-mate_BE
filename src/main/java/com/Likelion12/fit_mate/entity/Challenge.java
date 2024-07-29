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

    private String title; // 챌린지 제목
    private String description; // 챌린지 설명
    private LocalDate date; // 챌린지 완수 날짜
    private boolean completed; // 챌린지 성공 여부

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
