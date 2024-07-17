package com.Likelion12.fit_mate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 10)
    private Gender gender;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "profileImage", length = 255)
    private String profileImage;

    @Column(name = "registrationDate", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;

    // enum for gender
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
