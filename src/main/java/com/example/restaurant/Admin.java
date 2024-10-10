package com.example.restaurant;

import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.auth.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Admin {
    public Admin(UserRepository userRepository,
                 PasswordEncoder passwordEncoder){
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123456"))
                    .email("not.@gmail.com")
                    .phone("9876543210")
                    .role("ROLE_ADMIN")
                    .build();
            userRepository.save(admin);
        }
        if(userRepository.findByUsername("user2").isEmpty()){
            UserEntity user2 = UserEntity.builder()
                    .username("user2")
                    .password(passwordEncoder.encode("123456"))
                    .email("user2.@gmail.com")
                    .phone("00000000")
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user2);
        }
        if(userRepository.findByUsername("user3").isEmpty()){
            UserEntity user3 = UserEntity.builder()
                    .username("user3")
                    .password(passwordEncoder.encode("123456"))
                    .email("user3.@gmail.com")
                    .phone("01000000")
                    .role("ROLE_USER")
                    .build();
            userRepository.save(user3);
        }
    }




}
