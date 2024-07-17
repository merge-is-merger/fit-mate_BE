package com.Likelion12.fit_mate.controller;

import com.Likelion12.fit_mate.dto.request.LoginRequest;
import com.Likelion12.fit_mate.dto.request.RegisterRequest;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.service.AuthService;
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
            authService.register(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<Users> loginUser(@RequestBody LoginRequest request) {
        try {
            Users user = authService.login(request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }
}
