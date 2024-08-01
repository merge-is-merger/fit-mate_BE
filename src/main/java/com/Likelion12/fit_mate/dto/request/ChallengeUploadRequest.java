package com.Likelion12.fit_mate.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ChallengeUploadRequest {
    private Long userId;
    private Long challengeId;
    private MultipartFile photo;
}
