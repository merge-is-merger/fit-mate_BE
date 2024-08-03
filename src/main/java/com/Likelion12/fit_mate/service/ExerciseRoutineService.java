package com.Likelion12.fit_mate.service;

import com.Likelion12.fit_mate.dto.request.CreateRoutineRequest;
import com.Likelion12.fit_mate.dto.response.ExerciseDto;
import com.Likelion12.fit_mate.dto.response.RoutineResponse;
import com.Likelion12.fit_mate.entity.Exercise;
import com.Likelion12.fit_mate.entity.ExerciseRoutines;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.repository.ExerciseRepository;
import com.Likelion12.fit_mate.repository.ExerciseRoutinesRepository;
import com.Likelion12.fit_mate.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseRoutineService {

    private final ExerciseRoutinesRepository routineRepository;
    private final UsersRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseRoutineService(ExerciseRoutinesRepository routineRepository, UsersRepository userRepository, ExerciseRepository exerciseRepository) {
        this.routineRepository = routineRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public RoutineResponse createRoutine(String username, CreateRoutineRequest request) {
        // 사용자 이름으로 사용자 조회
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 새로운 운동 루틴 생성
        ExerciseRoutines routine = new ExerciseRoutines();
        routine.setName(request.getName());
        routine.setUser(user);

        // 운동 ID 리스트를 기반으로 운동 조회 및 설정
        LinkedHashSet<Exercise> exercises = request.getExerciseIds().stream()
                .map(id -> exerciseRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercise not found")))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        routine.setExercises(exercises);
        // 운동 루틴 저장
        ExerciseRoutines savedRoutine = routineRepository.save(routine);

        // 저장된 루틴을 DTO로 변환하여 반환
        return toDto(savedRoutine);
    }

    public List<RoutineResponse> getRoutinesByUser(String username) {
        // 사용자 이름으로 사용자 조회
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        // 사용자의 운동 루틴 조회
        List<ExerciseRoutines> routines = routineRepository.findByUser(user);

        // 조회된 루틴이 없으면 빈 리스트 반환
        if (routines.isEmpty()) {
            return List.of();
        }

        // 조회된 루틴을 DTO 리스트로 변환하여 반환
        return routines.stream().map(this::toDto).collect(Collectors.toList());
    }

    private RoutineResponse toDto(ExerciseRoutines routine) {
        RoutineResponse response = new RoutineResponse();
        response.setId(routine.getId());
        response.setName(routine.getName());
        response.setExercises(routine.getExercises().stream().map(exercise -> {
            ExerciseDto dto = new ExerciseDto();
            dto.setId(exercise.getId());
            dto.setName(exercise.getName());
            dto.setDescription(exercise.getDescription());
            dto.setCategoryId(exercise.getCategory().getId());
            return dto;
        }).collect(Collectors.toList()));
        return response;
    }
}
