package com.Likelion12.fit_mate.entity;

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
    private Long userId;  // 사용자 ID, Primary Key

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;  // 사용자 이름 (로그인용), Unique 제약조건 추가

    @Column(name = "password", nullable = false, length = 100)
    private String password;  // 비밀번호 (해싱된 값으로 저장)

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;  // 사용자 닉네임

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;  // 사용자 생년월일

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 10)
    private Gender gender;  // 사용자 성별, Enum 타입으로 정의

    @Column(name = "email", length = 100)
    private String email;  // 사용자 이메일 주소

    @Column(name = "profileImage", length = 255)
    private String profileImage;  // 사용자 프로필 이미지 경로 혹은 URL

    @Column(name = "registrationDate", nullable = false)
    private LocalDateTime registrationDate;  // 사용자 가입 일자

    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;  // 사용자 마지막 로그인 일시

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Routines> routines = new ArrayList<>();  // 사용자가 가지는 루틴 목록
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserChallenges> userChallenges = new ArrayList<>();  // 사용자가 참여하는 챌린지 목록

    // enum for gender
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
