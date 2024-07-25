package com.Likelion12.fit_mate.controller;

import com.Likelion12.fit_mate.entity.Exercise;
import com.Likelion12.fit_mate.entity.ExerciseCategories;
import com.Likelion12.fit_mate.entity.ExerciseRoutines;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.repository.ExerciseCategoriesRepository;
import com.Likelion12.fit_mate.repository.ExerciseRepository;
import com.Likelion12.fit_mate.repository.UsersRepository;
import com.Likelion12.fit_mate.service.ExerciseRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseRoutineController {

    private final ExerciseRoutineService routineService;

    private final ExerciseCategoriesRepository categoryRepository;

    private final UsersRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseRoutineController(ExerciseRoutineService routineService, ExerciseCategoriesRepository categoryRepository, UsersRepository userRepository, ExerciseRepository exerciseRepository) {
        this.routineService = routineService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }


    @GetMapping("/allData")
    public Map<String, Object> getAllData(Authentication authentication) {
        String username = authentication.getName();
        Users user = userRepository.findByUsername(username);

        List<ExerciseCategories> categories = categoryRepository.findAll();
        List<ExerciseRoutines> routines = routineService.getRoutinesByUser(username);

        Map<String, Object> response = new HashMap<>();
        response.put("categories", categories);
        response.put("userRoutine", Map.of(
                "userId", user.getUserId(),
                "routines", routines
        ));

        return response;
    }

    @PostMapping("/routines")
    public Map<String, Object> createRoutine(Authentication authentication, @RequestBody Map<String, Object> routineData) {
        String username = authentication.getName();

        String routineName = (String) routineData.get("name");
        List<Map<String, Object>> exercisesData = (List<Map<String, Object>>) routineData.get("exercises");

        ExerciseRoutines routine = new ExerciseRoutines();
        routine.setName(routineName);

        // 사용자 설정
        Users user = userRepository.findByUsername(username);
        routine.setUser(user);

        // 운동 설정
        Set<Exercise> exercises = new HashSet<>();
        for (Map<String, Object> exerciseData : exercisesData) {
            Long exerciseId = Long.valueOf((Integer) exerciseData.get("exerciseId"));
            Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new RuntimeException("Exercise not found"));

            exercises.add(exercise);
        }
        routine.setExercises(exercises);

        ExerciseRoutines savedRoutine = routineService.createRoutine(username, routine);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "루틴 저장 완료");
        response.put("routineId", savedRoutine.getId());

        return response;
    }
}

