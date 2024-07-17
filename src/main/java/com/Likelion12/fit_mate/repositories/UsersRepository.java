package com.Likelion12.fit_mate.repositories;

import com.Likelion12.fit_mate.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    // 사용자 이름(username)으로 사용자를 찾는 메서드
    Users findByUsername(String username);

    // 닉네임(nickname)으로 사용자를 찾는 메서드
    Users findByNickname(String nickname);

    // 이메일(email)으로 사용자를 찾는 메서드
    Users findByEmail(String email);
}
