package com.Likelion12.fit_mate.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegisterRequest {

    private String userId;
    private String password;
    private String confirmPassword;  // 패스워드 확인 필드 추가
    private String nickname;
    private MultipartFile profileImage;


}