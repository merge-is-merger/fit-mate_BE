package com.Likelion12.fit_mate.repository;

import com.Likelion12.fit_mate.entity.Challenge;
import com.Likelion12.fit_mate.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByUser(Users user);
    List<Challenge> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Optional<Challenge> findByTitle(String title); // 추가된 메서드
}
