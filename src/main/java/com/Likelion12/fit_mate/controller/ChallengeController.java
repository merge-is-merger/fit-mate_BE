package com.Likelion12.fit_mate.controller;

import com.Likelion12.fit_mate.dto.request.ChallengeUploadRequest;
import com.Likelion12.fit_mate.dto.response.ChallengeResponse;
import com.Likelion12.fit_mate.dto.response.ChallengeUploadResponse;
import com.Likelion12.fit_mate.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * @param authentication 인증 정보를 통해 사용자 정보를 가져옵니다.
     * @return 사용자와 챌린지 세부 정보를 포함한 챌린지 응답
     */
    @GetMapping("/challenge")
    public ResponseEntity<ChallengeResponse> getChallenge(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ChallengeResponse response = challengeService.getChallengesForUser(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    /**
     * 챌린지에 대한 사진을 업로드하는 엔드포인트입니다.
     * @param challengeId 챌린지의 ID
     * @param photo 업로드할 사진 파일
     * @return 업로드 성공 또는 실패를 나타내는 응답
     */
    @PostMapping("/challenge/upload")
    public ResponseEntity<ChallengeUploadResponse> uploadChallengePhoto(
            @RequestParam("challenge_id") Long challengeId,
            @RequestParam("photo") MultipartFile photo) {

        ChallengeUploadRequest request = new ChallengeUploadRequest();
        request.setChallengeId(challengeId);
        request.setPhoto(photo);

        ChallengeUploadResponse response = challengeService.uploadChallengePhoto(request);
        return ResponseEntity.ok(response);
    }
}
