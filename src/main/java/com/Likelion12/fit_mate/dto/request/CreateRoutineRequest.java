package com.Likelion12.fit_mate.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateRoutineRequest {
    private String username;
    private String name;
    private List<Long> exerciseIds;
}
