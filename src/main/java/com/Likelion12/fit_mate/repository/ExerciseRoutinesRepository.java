package com.Likelion12.fit_mate.repository;

import com.Likelion12.fit_mate.entity.ExerciseRoutines;
import com.Likelion12.fit_mate.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRoutinesRepository extends JpaRepository<ExerciseRoutines, Long> {
    List<ExerciseRoutines> findByUser(Users user);
}
