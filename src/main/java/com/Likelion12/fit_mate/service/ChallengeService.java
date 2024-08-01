package com.Likelion12.fit_mate.service;

import com.Likelion12.fit_mate.dto.request.ChallengeUploadRequest;
import com.Likelion12.fit_mate.dto.response.ChallengeResponse;
import com.Likelion12.fit_mate.dto.response.ChallengeUploadResponse;
import com.Likelion12.fit_mate.entity.Challenge;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.repository.ChallengeRepository;
import com.Likelion12.fit_mate.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UsersRepository usersRepository; // UsersRepository 추가
    private final Path fileStorageLocation;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository, UsersRepository usersRepository) {
        this.challengeRepository = challengeRepository;
        this.usersRepository = usersRepository; // 초기화
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("업로드된 파일이 저장될 디렉토리를 생성할 수 없습니다.", ex);
        }
    }

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

    public ChallengeUploadResponse uploadChallengePhoto(ChallengeUploadRequest request) {
        try {
            // 챌린지 ID 유효성 검사
            Challenge challenge = challengeRepository.findById(request.getChallengeId())
                    .orElseThrow(() -> new RuntimeException("챌린지를 찾을 수 없습니다."));

            // 사용자 ID 유효성 검사
            Users user = usersRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 파일을 로컬에 저장 (덮어쓰기)
            MultipartFile photo = request.getPhoto();
            String fileName = request.getChallengeId() + "_" + UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(photo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 사용자 정보 업데이트 (count 증가)
            user.setCount(user.getCount() + 1);
            usersRepository.save(user); // 변경된 사용자 정보 저장

            return new ChallengeUploadResponse("success", "챌린지가 성공적으로 인증되었습니다.");

        } catch (IOException ex) {
            throw new RuntimeException("파일을 저장할 수 없습니다. 다시 시도해 주세요!", ex);
        }
    }
}
