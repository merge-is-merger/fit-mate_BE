package com.Likelion12.fit_mate.dto.request;

import com.Likelion12.fit_mate.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequest {

    private String username;
    private String nickname;
    private String password;
    private String confirmPassword;  // 패스워드 확인 필드 추가
    private LocalDate birthdate;  // 사용자 생년월일
    private Users.Gender gender;
    private MultipartFile profileImage;


}