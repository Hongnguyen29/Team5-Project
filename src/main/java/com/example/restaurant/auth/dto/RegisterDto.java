package com.example.restaurant.auth.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String passwordCheck;
    private String email;
    private String phone;
}
