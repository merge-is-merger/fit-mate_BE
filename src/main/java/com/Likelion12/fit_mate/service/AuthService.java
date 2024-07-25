package com.Likelion12.fit_mate.service;

import com.Likelion12.fit_mate.dto.request.LoginRequest;
import com.Likelion12.fit_mate.dto.request.RegisterRequest;
import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.repository.UsersRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public void register(RegisterRequest request) throws IOException {
        if (usersRepository.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("ID already exists");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (usersRepository.findByNickname(request.getNickname()) != null) {
            throw new RuntimeException("Nickname already exists");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfileImage("https://fastly.picsum.photos/id/65/4912/3264.jpg?hmac=uq0IxYtPIqRKinGruj45KcPPzxDjQvErcxyS1tn7bG0");

        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            String imageUrl = uploadFile(request.getProfileImage());
            user.setProfileImage(imageUrl);
        }

        usersRepository.save(user);
    }

    public Users login(LoginRequest request) {
        Users user = usersRepository.findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            user.setLastLogin(LocalDateTime.now());
            usersRepository.save(user); // Update last login time
            return user;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    private String uploadFile(MultipartFile file) throws IOException {
        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        s3Client.putObject(bucket, key, file.getInputStream(), metadata);
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
    }
}
