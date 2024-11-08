package com.example.restaurant.auth;

import com.example.restaurant.auth.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationFacade facade;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto dto) {
        userService.register(dto);
        return ResponseEntity.ok("User registered successfully.");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequestDto requestDto) {
        JwtResponseDto responseDto = userService.loginUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/auth/profile")
    public ResponseEntity<?> profile() {
        UserDto infoUser = userService.profile();
        return ResponseEntity.ok(infoUser);
    }

    @PutMapping("/auth/updateInfo")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateDto dto) {
        UserDto updateUser = userService.updateInfo(dto);
        return ResponseEntity.ok(updateUser);
    }

    @PutMapping(value = "/auth/updateImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> profileImg(@RequestParam("file") MultipartFile file) {
        UserDto updatedUser = userService.updateImage(file);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/auth/password")
    public ResponseEntity<String> updatePass(@RequestBody Passwordto dto) {
        userService.updatePass(dto);
        return ResponseEntity.ok("Password changed successfully.");
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDto>> allUser() {
        List<UserDto> dtoList = userService.allUsers();
        return ResponseEntity.ok(dtoList);
    }
}
