package com.Likelion12.fit_mate.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChallengeResponse {

    private UserDTO user;

    @Getter @Setter
    public static class UserDTO {
        private String name;
        private String birthdate;
        private String gender;
        private int count;
        private String nickname;
    }

}
