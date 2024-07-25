package com.Likelion12.fit_mate.service;

import com.Likelion12.fit_mate.dto.response.ChallengeResponse;
import com.Likelion12.fit_mate.entity.Challenge;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final AuthService authService;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository, AuthService authService) {
        this.challengeRepository = challengeRepository;
        this.authService = authService;
    }

    public ChallengeResponse getChallengesForUser(String username) {
        Users user = authService.findByUsername(username);
        List<Challenge> challenges = challengeRepository.findByUser(user);

        ChallengeResponse response = new ChallengeResponse();

        ChallengeResponse.UserDTO userDTO = new ChallengeResponse.UserDTO();
        userDTO.setName(user.getUsername());
        userDTO.setBirthdate(user.getBirthdate().toString());
        userDTO.setGender(user.getGender().name());  // Convert enum to String

        ChallengeResponse.ChallengeDTO challengeDTO = new ChallengeResponse.ChallengeDTO();
        challengeDTO.setCount(challenges.size());
        List<ChallengeResponse.RecordDTO> records = challenges.stream()
                .map(challenge -> {
                    ChallengeResponse.RecordDTO recordDTO = new ChallengeResponse.RecordDTO();
                    recordDTO.setDate(challenge.getDate());
                    recordDTO.setCompleted(challenge.isCompleted());
                    return recordDTO;
                })
                .collect(Collectors.toList());
        challengeDTO.setRecords(records);

        response.setUser(userDTO);
        response.setChallenge(challengeDTO);

        return response;
    }
}
