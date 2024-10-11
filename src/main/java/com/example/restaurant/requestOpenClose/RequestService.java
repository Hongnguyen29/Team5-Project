package com.example.restaurant.requestOpenClose;

import com.example.restaurant.ImageFileUtils;
import com.example.restaurant.auth.AuthenticationFacade;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.auth.repo.UserRepository;
import com.example.restaurant.enumList.RequestStatus;
import com.example.restaurant.enumList.RestStatus;
import com.example.restaurant.requestOpenClose.dto.CloseViewDto;
import com.example.restaurant.requestOpenClose.dto.OpenConfirmDto;
import com.example.restaurant.requestOpenClose.dto.OpenDto;
import com.example.restaurant.requestOpenClose.dto.OpenViewDto;
import com.example.restaurant.requestOpenClose.entity.CloseRequestEntity;
import com.example.restaurant.requestOpenClose.entity.OpenRequestEntity;
import com.example.restaurant.requestOpenClose.repo.CloseRequestRepository;
import com.example.restaurant.requestOpenClose.repo.OpenRequestRepository;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
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
        if(openRepository.existsByStatusAndUser(RequestStatus.PENDING,user)){
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
        openRequest.setStatus(RequestStatus.PENDING);
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
        if(!(openRequest.getStatus().equals(RequestStatus.PENDING))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(dto.isApproved()){
            openRequest.setStatus(RequestStatus.ACCEPTED);
            openRequest.setProcessedAt(LocalDateTime.now());
            openRepository.save(openRequest);
            log.info(openRequest.getUser().toString());

            RestaurantEntity restaurant = RestaurantEntity.builder()
                    .nameRestaurant(openRequest.getNameRestaurant())
                    .restNumber(openRequest.getRestNumber())
                    .ownerName(openRequest.getOwnerName())
                    .ownerIdNo(openRequest.getOwnerIdNo())
                    .status(RestStatus.PREPARING)
                    .user(openRequest.getUser())
                    .build();
            restRepository.save(restaurant);
            log.info(restaurant.getUser().toString());
            UserEntity user = openRequest.getUser();
            if(openRequest.getStatus().equals(RequestStatus.ACCEPTED)){
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
            openRequest.setStatus(RequestStatus.REJECTED);
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


        if(!(user.getUsername().equals(openRequest.getUser().getUsername()) || user.getUsername().equals("admin"))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return OpenViewDto.fromEntity(openRequest);
    }

    public List<OpenViewDto> adminReadAll(RequestStatus status){  // "PENDING"  "ACCEPTED"   "REJECTED"
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

    // CLOSE RESTAURANT
    @Transactional
    public CloseViewDto closeRestaurant(String reason){
        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = user.getRestaurant();
        if((restaurant.getStatus().equals(RestStatus.CLOSE))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if(closeRepository.existsByRestaurant(restaurant)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if(reason == null || reason.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"Please check the reason");
        }
        CloseRequestEntity closeRequest = new CloseRequestEntity();
        closeRequest.setReason(reason);
        closeRequest.setCreatedAt(LocalDateTime.now());
        closeRequest.setStatus(RequestStatus.PENDING);
        closeRequest.setRestaurant(restaurant);
        closeRepository.save(closeRequest);

        return CloseViewDto.fromEntity(closeRequest);

    }
    @Transactional
    public CloseViewDto closeConfirm(Long closeId){
        CloseRequestEntity closeRequest = closeRepository
                .findById(closeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        RestaurantEntity restaurant = closeRequest.getRestaurant();
        if((restaurant.getStatus().equals(RestStatus.CLOSE))){

            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        closeRequest.setStatus(RequestStatus.ACCEPTED);
        closeRequest.setProcessedAt(LocalDateTime.now());
        closeRepository.save(closeRequest);

        UserEntity user = restaurant.getUser();
        if(closeRequest.getStatus().equals(RequestStatus.ACCEPTED)){
            restaurant.setStatus(RestStatus.CLOSE);
            restRepository.save(restaurant);
            user.setRole("ROLE_USER");
            userRepository.save(user);
        }
        return CloseViewDto.fromEntity(closeRequest);
    }
    public CloseViewDto readOneClose(Long closeId){
        CloseRequestEntity closeRequest = closeRepository.findById(closeId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity user = facade.extractUser();


        System.out.println(user.getUsername());
        System.out.println(closeRequest.getRestaurant().getUser().getUsername());
        if(!(user.getUsername().equals(closeRequest.getRestaurant().getUser().getUsername())
                || user.getUsername().equals("admin"))){

            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return CloseViewDto.fromEntity(closeRequest);
    }
    public List<CloseViewDto> adminReadAllClose (RequestStatus status){  // "PENDING"  "ACCEPTED"   "REJECTED"
        List<CloseViewDto> viewDtoList = new ArrayList<>();
        if(status == null){
            List<CloseRequestEntity> closeList = closeRepository.findAllOrderedByStatusAndCreatedAt();
            for ( CloseRequestEntity o : closeList){
                viewDtoList.add(CloseViewDto.fromEntity(o));
            }
        }
        else {
            List<CloseRequestEntity> closeList = closeRepository.findByStatusOrderByCreatedAtDesc(status);
            for ( CloseRequestEntity o : closeList){
                viewDtoList.add(CloseViewDto.fromEntity(o));
            }
        }
        return viewDtoList;
    }
    public List<CloseViewDto> userReadAllClose(){
        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = user.getRestaurant();
        List<CloseViewDto> viewDtoList = new ArrayList<>();
        List<CloseRequestEntity> closeList = closeRepository.findByRestaurantOrderByCreatedAtDesc(restaurant);
        for ( CloseRequestEntity o : closeList){
            viewDtoList.add(CloseViewDto.fromEntity(o));
        }
        return viewDtoList;
    }

    






}
