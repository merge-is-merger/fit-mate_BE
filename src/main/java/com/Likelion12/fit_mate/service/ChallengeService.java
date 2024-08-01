package com.Likelion12.fit_mate.service;

import com.Likelion12.fit_mate.dto.request.ChallengeUploadRequest;
import com.Likelion12.fit_mate.dto.response.ChallengeResponse;
import com.Likelion12.fit_mate.dto.response.ChallengeUploadResponse;
import com.Likelion12.fit_mate.entity.Challenge;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.repository.ChallengeRepository;
import com.Likelion12.fit_mate.repository.UsersRepository;  // 추가
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UsersRepository usersRepository;  // 추가
    private final Path fileStorageLocation;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository, UsersRepository usersRepository) {
        this.challengeRepository = challengeRepository;
        this.usersRepository = usersRepository;  // 추가
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("업로드된 파일이 저장될 디렉토리를 생성할 수 없습니다.", ex);
        }
    }

    /**
     * 특정 사용자의 챌린지 정보를 가져옵니다.
     * @param userId 사용자의 ID
     * @return 사용자와 챌린지 세부 정보를 포함한 챌린지 응답
     */
    public ChallengeResponse getChallengesForUser(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Challenge> challenges = challengeRepository.findByUser(user);

        ChallengeResponse response = new ChallengeResponse();

        ChallengeResponse.UserDTO userDTO = new ChallengeResponse.UserDTO();
        userDTO.setName(user.getUsername());
        userDTO.setBirthdate(user.getBirthdate().toString());
        userDTO.setGender(user.getGender().name());  // Enum을 문자열로 변환

        ChallengeResponse.ChallengeDTO challengeDTO = new ChallengeResponse.ChallengeDTO();
        challengeDTO.setCount(challenges.size());
        List<ChallengeResponse.RecordDTO> records = challenges.stream()
                .map(challenge -> {
                    ChallengeResponse.RecordDTO recordDTO = new ChallengeResponse.RecordDTO();
                    recordDTO.setDate(challenge.getDate().toString());
                    recordDTO.setCompleted(challenge.isCompleted());
                    return recordDTO;
                })
                .collect(Collectors.toList());
        challengeDTO.setRecords(records);

        response.setUser(userDTO);
        response.setChallenge(challengeDTO);

        return response;
    }

    /**
     * 이번 달의 챌린지를 가져옵니다.
     * @return 이번 달의 챌린지 리스트
     */
    public List<Challenge> getMonthlyChallenges() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        return challengeRepository.findByDateBetween(startOfMonth, endOfMonth);
    }

    /**
     * 특정 챌린지에 대한 사진을 업로드합니다.
     * @param request 챌린지 ID와 사진 파일을 포함한 요청
     * @return 업로드 성공 또는 실패를 나타내는 응답
     */
    public ChallengeUploadResponse uploadChallengePhoto(ChallengeUploadRequest request) {
        try {
            // 챌린지 ID 유효성 검사
            Challenge challenge = challengeRepository.findById(request.getChallengeId())
                    .orElseThrow(() -> new RuntimeException("챌린지를 찾을 수 없습니다."));

            // 파일을 로컬에 저장 (또는 S3 등에 업로드)
            MultipartFile photo = request.getPhoto();
            String fileName = request.getChallengeId() + "_" + photo.getOriginalFilename();
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(photo.getInputStream(), targetLocation);

            // 선택적으로, 챌린지 엔티티에 사진 URL/경로를 포함하도록 업데이트할 수 있습니다.
            // challenge.setPhotoUrl(fileName);
            // challengeRepository.save(challenge);

            return new ChallengeUploadResponse("success", "챌린지가 성공적으로 인증되었습니다.");

        } catch (IOException ex) {
            throw new RuntimeException("파일을 저장할 수 없습니다. 다시 시도해 주세요!", ex);
        }
    }
}
