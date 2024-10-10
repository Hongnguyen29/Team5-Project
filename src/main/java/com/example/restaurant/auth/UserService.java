package com.example.restaurant.auth;

import com.example.restaurant.ImageFileUtils;
import com.example.restaurant.auth.dto.*;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.auth.jwt.JwtTokenUtils;
import com.example.restaurant.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationFacade authFacade;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final CustomUserDetailsService customService;
    private final ImageFileUtils imageFileUtils;

    @Transactional
    public void register(RegisterDto dto){
        if (customService.userExists(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already exists");
        if (!dto.getPassword().equals(dto.getPasswordCheck()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password and password confirmation do not match");
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already exists");
        }
        if(userRepository.existsByPhone(dto.getPhone())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phone already exists");
        }
        UserEntity newUser = new UserEntity();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setEmail(dto.getEmail());
        newUser.setPhone(dto.getPhone());
        newUser.setRole("ROLE_USER");
        userRepository.save(newUser);
       // UserDto.fromEntity(newUser);
    }

    public JwtResponseDto loginUser(
            JwtRequestDto requestDto
    ){
        if (requestDto.getUsername() == null || requestDto.getPassword() == null) {
            throw new IllegalArgumentException("Username and password must not be null.");
        }
        UserDetails userDetails;
        try {
            userDetails = customService.loadUserByUsername(requestDto.getUsername());
        } catch (UsernameNotFoundException ex) {
            throw new IllegalArgumentException("Username not found.");
        }
        if(!passwordEncoder.matches(
                requestDto.getPassword(), userDetails.getPassword()))
            throw new IllegalArgumentException( "Incorrect password !");
        String jwt = jwtTokenUtils.generateToken(userDetails);
        JwtResponseDto responseDto = new JwtResponseDto();
        responseDto.setToken(jwt);
        return responseDto;
    }
    public UserDto profile(){
        UserEntity user = authFacade.extractUser();
        return UserDto.fromEntity(user);
    }

    public UserDto updateInfo(UpdateDto dto){

        if (userRepository.existsByEmail(dto.getEmail())) {
            System.out.println("check mail");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        if (userRepository.existsByPhone(dto.getPhone())) {
            System.out.println("check phone");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone already exists");
        }
        UserEntity user = authFacade.extractUser();

        if(dto.getEmail() != null){
            user.setEmail(dto.getEmail());}
        if(dto.getPhone() != null){
            user.setPhone(dto.getPhone());
        }
        userRepository.save(user);
        return UserDto.fromEntity(user);
    }
    public UserDto updateImage(MultipartFile file){
        UserEntity user = authFacade.extractUser();
        String path = imageFileUtils.saveFile(
                String.format("users/%d/",user.getId()), "profile",file
        );
        user.setImage(path);
        return UserDto.fromEntity(userRepository.save(user));
    }
    public void updatePass(Passwordto dto){
        UserEntity user = authFacade.extractUser();
        if(!passwordEncoder.matches(
                dto.getOldPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Incorrect password !");
        if (!dto.getPassword().equals(dto.getPasswordCheck()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password and password confirmation do not match");

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
         userRepository.save(user);
    }




}
