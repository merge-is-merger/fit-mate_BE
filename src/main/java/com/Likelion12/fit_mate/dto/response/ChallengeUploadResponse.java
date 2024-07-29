package com.Likelion12.fit_mate.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeUploadResponse {
    private String status;
    private String message;

    public ChallengeUploadResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
