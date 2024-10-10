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

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationFacade facade;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto dto) {
        try {
            userService.register(dto);
            return ResponseEntity.ok("User registered successfully.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody
            JwtRequestDto requestDto) {
        try {
            JwtResponseDto responseDto = userService.loginUser(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
    @GetMapping("/auth/profile")
    public ResponseEntity<?> profile(){
        try {
            UserDto infoUser= userService.profile();
            return ResponseEntity.ok(infoUser);
        }catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE, "Error");
        }

    }
    @PutMapping("/auth/updateInfo")
    public ResponseEntity<?> updateProfile(
            @RequestBody
            UpdateDto dto
    ) {
        try {
            UserDto updateUser = userService.updateInfo(dto);
            return ResponseEntity.ok(updateUser);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }
    @PutMapping(
            value = "/auth/updateImage",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public UserDto profileImg(
            @RequestParam("file")
            MultipartFile file
    ) {
        return userService.updateImage(file);
    }
    @PutMapping("/auth/password")
    public ResponseEntity<String> updatePass(
            @RequestBody
            Passwordto dto
    ){
        try {
            userService.updatePass(dto);
            return ResponseEntity.ok( "Password changed successfully.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }

    }
}
