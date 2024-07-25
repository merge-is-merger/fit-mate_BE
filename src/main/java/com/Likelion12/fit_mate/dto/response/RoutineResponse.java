package com.Likelion12.fit_mate.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoutineResponse {
    private Long id;
    private String name;
    private List<ExerciseDto> exercises;
}