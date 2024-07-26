package com.Likelion12.fit_mate.controller;

import com.Likelion12.fit_mate.dto.request.LoginRequest;
import com.Likelion12.fit_mate.dto.request.RegisterRequest;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원가입 엔드포인트
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@ModelAttribute RegisterRequest request) {
        try {
            System.out.println("Username: " + request.getUsername());
            System.out.println("Password: " + request.getPassword());
            System.out.println("Confirm Password: " + request.getConfirmPassword());
            System.out.println("Nickname: " + request.getNickname());
            if (request.getProfileImage() != null) {
                System.out.println("Profile Image: " + request.getProfileImage().getOriginalFilename());
            } else {
                System.out.println("Profile Image is null");
            }
            authService.register(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        try {
            Users user = authService.login(request);
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("user", user); // 세션에 사용자 정보 저장
            return ResponseEntity.ok("Logged in successfully");
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    // 로그아웃 엔드포인트
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return ResponseEntity.ok("Logged out successfully");
    }
}