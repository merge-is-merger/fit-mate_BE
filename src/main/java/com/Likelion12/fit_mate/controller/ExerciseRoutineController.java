package com.Likelion12.fit_mate.controller;

import com.Likelion12.fit_mate.dto.request.CreateRoutineRequest;
import com.Likelion12.fit_mate.dto.response.RoutineResponse;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.repository.UsersRepository;
import com.Likelion12.fit_mate.service.ExerciseRoutineService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routine")
public class ExerciseRoutineController {

    private final ExerciseRoutineService routineService;
    private final UsersRepository userRepository;

    @Autowired
    public ExerciseRoutineController(ExerciseRoutineService routineService, UsersRepository userRepository) {
        this.routineService = routineService;
        this.userRepository = userRepository;
    }

    // 특정 사용자의 루틴만 조회하는 엔드포인트
    @GetMapping("/my")
    public Map<String, Object> getUserRoutine(@RequestParam String username) {
        Users user = userRepository.findByUsername(username);

        // 특정 사용자의 모든 루틴 조회
        List<RoutineResponse> routines = routineService.getRoutinesByUser(username);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("routines", routines);

        // 루틴이 없는 경우 메시지 추가
        if (routines.isEmpty()) {
            response.put("message", "루틴이 없습니다. 새로운 루틴을 추가해주세요.");
        }

        return response;
    }

    // 새로운 루틴을 생성하는 엔드포인트
    @PostMapping("/save")
    public Map<String, Object> createRoutine(@RequestBody CreateRoutineRequest request, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new RuntimeException("Unauthorized");
        }
        Users user = (Users) session.getAttribute("user");
        String username = user.getUsername(); // 인증된 사용자 이름 가져오기

        // 새로운 루틴 생성
        RoutineResponse savedRoutine = routineService.createRoutine(username, request);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("message", "루틴 저장 완료");
        response.put("routineId", savedRoutine.getId());

        return response;
    }
}