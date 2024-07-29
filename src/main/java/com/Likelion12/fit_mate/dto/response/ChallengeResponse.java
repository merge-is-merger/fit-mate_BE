package com.Likelion12.fit_mate.dto.response;

import com.Likelion12.fit_mate.entity.Challenge;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ChallengeResponse {

    private UserDTO user;
    private ChallengeDTO challenge;
    private List<Challenge> challenges;
    private List<Challenge> monthlyChallenges;

    @Getter @Setter
    public static class UserDTO {
        private String name;
        private String birthdate;
        private String gender;
    }

    @Getter @Setter
    public static class ChallengeDTO {
        private int count;
        private List<RecordDTO> records;
    }

    @Getter @Setter
    public static class RecordDTO {
        private String date;
        private boolean completed;
    }
}
