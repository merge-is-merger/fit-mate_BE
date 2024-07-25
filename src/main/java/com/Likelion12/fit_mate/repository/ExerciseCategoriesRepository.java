package com.Likelion12.fit_mate.repository;

import com.Likelion12.fit_mate.entity.ExerciseCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseCategoriesRepository extends JpaRepository<ExerciseCategories, Long> {
    ExerciseCategories findByName(String categoryName);
}
