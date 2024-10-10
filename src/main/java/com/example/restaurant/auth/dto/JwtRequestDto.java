package com.example.restaurant.auth.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}