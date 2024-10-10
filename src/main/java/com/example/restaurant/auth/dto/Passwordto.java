package com.example.restaurant.auth.dto;

import lombok.Data;

@Data
public class Passwordto {
    private String oldPassword;
    private String password;
    private String passwordCheck;
}
