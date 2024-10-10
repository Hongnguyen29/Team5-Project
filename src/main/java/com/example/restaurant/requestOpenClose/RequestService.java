package com.example.restaurant.requestOpenClose;

import com.example.restaurant.ImageFileUtils;
import com.example.restaurant.auth.AuthenticationFacade;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.auth.repo.UserRepository;
import com.example.restaurant.requestOpenClose.dto.OpenConfirmDto;
import com.example.restaurant.requestOpenClose.dto.OpenDto;
import com.example.restaurant.requestOpenClose.dto.OpenViewDto;
import com.example.restaurant.requestOpenClose.entity.OpenRequestEntity;
import com.example.restaurant.requestOpenClose.repo.CloseRequestRepository;
import com.example.restaurant.requestOpenClose.repo.OpenRequestRepository;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class RequestService {
    private final UserRepository userRepository;
    private final OpenRequestRepository openRepository;
    private final CloseRequestRepository closeRepository;
    private final RestaurantRepository restRepository;
    private final AuthenticationFacade facade;
    private final ImageFileUtils imageFileUtils;

    @Transactional
    public OpenViewDto openRestaurant(OpenDto dto){
        UserEntity user = facade.extractUser();
        OpenRequestEntity openRequest = new OpenRequestEntity();
        if(openRepository.existsByStatusAndUser("PENDING",user)){
            System.out.println("hihi");
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,"User already has a pending request.");
        }
        String pathRestNo = imageFileUtils.saveFile(
                String.format("users/%d/",user.getId()),
                "restaurantNumber", dto.getImageRestNumber()
        );
        String pathIdNo = imageFileUtils.saveFile(
                String.format("users/%d/",user.getId()),
                user.getUsername()+"idNo", dto.getImageId()
        );
        openRequest.setNameRestaurant(dto.getNameRestaurant());
        openRequest.setRestNumber(dto.getRestNumber());
        openRequest.setImageRestNumber(pathRestNo);
        openRequest.setOwnerName(dto.getOwnerName());
        openRequest.setOwnerIdNo(dto.getOwnerIdNo());
        openRequest.setImageId(pathIdNo);
        openRequest.setStatus("PENDING");
        openRequest.setCreatedAt(LocalDateTime.now());
        openRequest.setUser(user);

        openRepository.save(openRequest);
        return OpenViewDto.fromEntity(openRequest);
    }

    @Transactional
    public OpenViewDto openConfirm(
            Long openId,
            OpenConfirmDto dto
            ){
        OpenRequestEntity openRequest = openRepository.findById(openId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(openRequest.getStatus().equals("ACCEPTED") ||
                openRequest.getStatus().equals("REJECTED")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(dto.isApproved()){
            openRequest.setStatus("ACCEPTED");
            openRequest.setProcessedAt(LocalDateTime.now());
            openRepository.save(openRequest);
            log.info(openRequest.getUser().toString());

            RestaurantEntity restaurant = RestaurantEntity.builder()
                    .nameRestaurant(openRequest.getNameRestaurant())
                    .restNumber(openRequest.getRestNumber())
                    .ownerName(openRequest.getOwnerName())
                    .ownerIdNo(openRequest.getOwnerIdNo())
                    .user(openRequest.getUser())
                    .build();
            restRepository.save(restaurant);
            log.info(restaurant.getUser().toString());
            UserEntity user = openRequest.getUser();
            if(openRequest.getStatus().equals("ACCEPTED")){
                user.setRole("ROLE_OWNER");
            }
            userRepository.save(user);
        }
        else {
            if(dto.getReason() == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,"The reason cannot be left blank");
            }
            openRequest.setReason(dto.getReason());
            openRequest.setStatus("REJECTED");
            openRequest.setProcessedAt(LocalDateTime.now());

            openRepository.save(openRequest);
        }
        return OpenViewDto.fromEntity(openRequest);
    }
    public OpenViewDto readOne(Long openId){
        OpenRequestEntity openRequest = openRepository.findById(openId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity user = facade.extractUser();
        System.out.println(user.getUsername());
        System.out.println(openRequest.getUser().getUsername());

        if(!(user.getUsername().equals(openRequest.getUser().getUsername()) || user.getUsername().equals("admin"))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }//(!(username1.equals("admin") || username1.equals(username2)))
        return OpenViewDto.fromEntity(openRequest);
    }

    public List<OpenViewDto> adminReadAll(String status){  // "PENDING"  "ACCEPTED"   "REJECTED"
        List<OpenViewDto> viewDtoList = new ArrayList<>();
        if(status == null){
            List<OpenRequestEntity> openList = openRepository.findAllOrderedByStatusAndCreatedAt();
            for ( OpenRequestEntity o : openList){
                viewDtoList.add(OpenViewDto.fromEntity(o));
            }
        }
        else {
            List<OpenRequestEntity> openList = openRepository.findByStatusOrderByCreatedAtDesc(status);
            for ( OpenRequestEntity o : openList){
                viewDtoList.add(OpenViewDto.fromEntity(o));
            }
        }
        return viewDtoList;
    }
    public List<OpenViewDto> userReadAll(){
        UserEntity user = facade.extractUser();
        List<OpenViewDto> viewDtoList = new ArrayList<>();
        List<OpenRequestEntity> openList = openRepository.findByUserOrderByCreatedAtDesc(user);
        for ( OpenRequestEntity o : openList){
            viewDtoList.add(OpenViewDto.fromEntity(o));
        }
        return viewDtoList;
    }





}
