package com.Likelion12.fit_mate.service;

import com.Likelion12.fit_mate.entity.Users;
import com.Likelion12.fit_mate.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to find user: " + username); // 디버깅 로그
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User not found: " + username); // 디버깅 로그
        }
        System.out.println("User found: " + username);  // 디버깅 로그
        System.out.println("User password: " + user.getPassword()); // 디버깅 로그
        return new CustomUserDetails(user);
    }
}
