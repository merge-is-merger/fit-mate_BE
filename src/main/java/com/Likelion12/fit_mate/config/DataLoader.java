package com.Likelion12.fit_mate.config;

import com.Likelion12.fit_mate.entity.Challenge;
import com.Likelion12.fit_mate.repository.ChallengeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ChallengeRepository challengeRepository) {
        return args -> {
            insertChallengeIfNotExists(challengeRepository, "요가 챌린지", "매일 30분씩 요가를 실천하여 유연성과 정신적 안정감을 향상시키세요");
            insertChallengeIfNotExists(challengeRepository, "10,000걸음 걷기 챌린지", "하루에 10,000걸음을 목표로 걷기를 실천하세요. 일상에서 더 많이 움직이며 건강을 증진시킬 수 있습니다.");
            insertChallengeIfNotExists(challengeRepository, "스쿼트 챌린지", "매일 스쿼트를 통해 하체 근육을 강화하세요. 첫날 20회로 시작해, 매일 조금씩 횟수를 늘려보세요");
            insertChallengeIfNotExists(challengeRepository, "하체 운동하기 챌린지", "하루에 3세트씩 하는 것을 목표로 해보세요. 횟수는 천천히 늘려가도 좋습니다.");
        };
    }

    private void insertChallengeIfNotExists(ChallengeRepository challengeRepository, String title, String description) {
        Optional<Challenge> existingChallenge = challengeRepository.findByTitle(title);
        if (existingChallenge.isEmpty()) {
            Challenge challenge = new Challenge(null, title, description, LocalDate.now(), false, null);
            challengeRepository.save(challenge);
        }
    }
}
