package com.Likelion12.fit_mate.controller;

import com.Likelion12.fit_mate.dto.request.ChallengeUploadRequest;
import com.Likelion12.fit_mate.dto.response.ChallengeResponse;
import com.Likelion12.fit_mate.dto.response.ChallengeUploadResponse;
import com.Likelion12.fit_mate.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    /**
     * 특정 사용자의 챌린지 정보를 가져옵니다.
     * @param userId 사용자의 ID
     * @return 사용자와 챌린지 세부 정보를 포함한 챌린지 응답
     */
    @GetMapping("/challenge")
    public ResponseEntity<ChallengeResponse> getChallenge(@RequestParam Long userId) {
        ChallengeResponse response = challengeService.getChallengesForUser(userId);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/challenge/upload")
    public ResponseEntity<ChallengeUploadResponse> uploadChallengePhoto(@ModelAttribute ChallengeUploadRequest request) {
        ChallengeUploadResponse response = challengeService.uploadChallengePhoto(request);
        return ResponseEntity.ok(response);
    }

}
