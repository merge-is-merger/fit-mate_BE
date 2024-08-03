package com.Likelion12.fit_mate.config;

import com.Likelion12.fit_mate.entity.Exercise;
import com.Likelion12.fit_mate.entity.ExerciseCategories;
import com.Likelion12.fit_mate.repository.ExerciseCategoriesRepository;
import com.Likelion12.fit_mate.repository.ExerciseRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    private final ExerciseRepository exerciseRepository;

    private final ExerciseCategoriesRepository categoriesRepository;

    public DataInitializer(ExerciseRepository exerciseRepository, ExerciseCategoriesRepository categoriesRepository) {
        this.exerciseRepository = exerciseRepository;
        this.categoriesRepository = categoriesRepository;
    }

    // 애플리케이션 시작 시 데이터베이스 초기화를 수행하는 메서드
    @PostConstruct
    public void init() {
        // Exercise 테이블에 데이터가 없는 경우에만 초기화 수행
        if (exerciseRepository.count() == 0) {
            try {
                // CSV 파일로부터 운동 데이터를 읽어옴
                List<Exercise> exercises = readExercisesFromCSV();
                // 읽어온 운동 데이터를 데이터베이스에 저장
                exerciseRepository.saveAll(exercises);
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }
        }
    }

    // CSV 파일을 읽어 운동 리스트를 반환하는 메서드
    private List<Exercise> readExercisesFromCSV() throws IOException, CsvValidationException {
        List<Exercise> exercises = new ArrayList<>();
        // CSVReader를 사용하여 CSV 파일을 읽음
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource("exercises_with_categories_ko.csv").getInputStream(), StandardCharsets.UTF_8))) {
            String[] nextLine;
            reader.readNext(); // 헤더를 건너뜀
            while ((nextLine = reader.readNext()) != null) {
                String name = nextLine[0];
                String description = nextLine[1];
                String categoryName = nextLine[2];

                // 카테고리 이름을 기준으로 데이터베이스에서 카테고리를 조회
                ExerciseCategories category = categoriesRepository.findByName(categoryName);
                // 카테고리가 존재하지 않는 경우 새로 생성하여 저장
                if (category == null) {
                    category = new ExerciseCategories();
                    category.setName(categoryName);
                    categoriesRepository.save(category);
                }

                // Exercise 객체를 생성하고 리스트에 추가
                exercises.add(new Exercise(name, description, category));
            }
        }
        return exercises;
    }
}
