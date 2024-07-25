package com.Likelion12.fit_mate.controller;

import com.Likelion12.fit_mate.dto.response.ChallengeResponse;
import com.Likelion12.fit_mate.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping("/challenge")
    public ResponseEntity<ChallengeResponse> getChallenge(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ChallengeResponse response = challengeService.getChallengesForUser(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
