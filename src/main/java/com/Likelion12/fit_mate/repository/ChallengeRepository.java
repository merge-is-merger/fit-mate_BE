package com.Likelion12.fit_mate.repository;

import com.Likelion12.fit_mate.entity.Challenge;
import com.Likelion12.fit_mate.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByUser(Users user);
}
