package com.Likelion12.fit_mate.dto.response;

import java.time.LocalDate;
import java.util.List;

public class ChallengeResponse {
    private UserDTO user;
    private ChallengeDTO challenge;

    // Getters and Setters

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ChallengeDTO getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeDTO challenge) {
        this.challenge = challenge;
    }

    public static class UserDTO {
        private String name;
        private String birthdate;
        private String gender;

        // Getters and Setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    public static class ChallengeDTO {
        private int count;
        private List<RecordDTO> records;

        // Getters and Setters

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<RecordDTO> getRecords() {
            return records;
        }

        public void setRecords(List<RecordDTO> records) {
            this.records = records;
        }
    }

    public static class RecordDTO {
        private LocalDate date;
        private boolean completed;

        // Getters and Setters

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
