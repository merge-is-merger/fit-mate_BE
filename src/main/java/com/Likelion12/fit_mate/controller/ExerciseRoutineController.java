package com.Likelion12.fit_mate.controller;

import com.Likelion12.fit_mate.dto.request.CreateRoutineRequest;
import com.Likelion12.fit_mate.dto.response.RoutineResponse;
import com.Likelion12.fit_mate.entity.ExerciseCategories;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.repository.ExerciseCategoriesRepository;
import com.Likelion12.fit_mate.repository.ExerciseRepository;
import com.Likelion12.fit_mate.repository.UsersRepository;
import com.Likelion12.fit_mate.service.ExerciseRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 특정 사용자의 모든 루틴과 관련 데이터를 조회하는 엔드포인트
    @GetMapping("/allData")
    public Map<String, Object> getAllData(@RequestParam String username) {
        Users user = userRepository.findByUsername(username);

        // 모든 카테고리 조회
        List<ExerciseCategories> categories = categoryRepository.findAll();
        // 특정 사용자의 모든 루틴 조회
        List<RoutineResponse> routines = routineService.getRoutinesByUser(username);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categories);
        response.put("userRoutine", Map.of(
                "userId", user.getUserId(),
                "routines", routines
        ));

        return response;
    }

    // 새로운 루틴을 생성하는 엔드포인트
    @PostMapping("/routines")
    public Map<String, Object> createRoutine(@RequestBody CreateRoutineRequest request) {
        String username = request.getUsername(); // 요청 바디에서 사용자 이름을 가져옴

        // 새로운 루틴 생성
        RoutineResponse savedRoutine = routineService.createRoutine(username, request);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("message", "루틴 저장 완료");
        response.put("routineId", savedRoutine.getId());

        return response;
    }
}
