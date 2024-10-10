package com.example.restaurant.auth.dto;

import com.example.restaurant.auth.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
@Builder
@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String image;
    private List<String> role;

    public static UserDto fromEntity(UserEntity entity) {
        List<String> roles = Arrays.stream(entity.getRole().split(","))
                .toList();
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .image(entity.getImage())
                .role(roles)
                .build();
    }
}
