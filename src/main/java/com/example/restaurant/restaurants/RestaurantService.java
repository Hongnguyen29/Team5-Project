package com.example.restaurant.restaurants;

import com.example.restaurant.ImageFileUtils;
import com.example.restaurant.auth.AuthenticationFacade;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.auth.repo.UserRepository;
import com.example.restaurant.enumList.RestStatus;
import com.example.restaurant.restaurants.dto.RestaurantDto;
import com.example.restaurant.restaurants.dto.RestaurantViewDto;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final UserRepository userRepository;
    private final RestaurantRepository restRepository;
    private final AuthenticationFacade facade;
    private final ImageFileUtils imageFileUtils;

    @Transactional
    public RestaurantViewDto updateInfo(RestaurantDto dto){
        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = user.getRestaurant();
        if(dto.getAddress() != null && !(dto.getAddress().isEmpty())) {
            restaurant.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null && !(dto.getPhone().isEmpty())){
            restaurant.setPhone(dto.getPhone());
        }
        if(dto.getDescription() != null && !(dto.getDescription().isEmpty())){
            restaurant.setDescription(dto.getDescription());
        }
        if(dto.getCategory() != null){
            restaurant.setCategory(dto.getCategory());
        }
        if(dto.getOpenTime() != null){
            restaurant.setOpenTime(dto.getOpenTime());
        }
        if(dto.getCloseTime() != null){
            restaurant.setCloseTime(dto.getCloseTime());
        }
        if(dto.getAddress() != null
                && dto.getPhone() != null
                && dto.getDescription() != null
                && dto.getCategory() != null
                && dto.getOpenTime() != null
                && dto.getCloseTime() != null ){
            restaurant.setStatus(RestStatus.OPEN);
        }

        restRepository.save(restaurant);

        return RestaurantViewDto.fromEntity(restaurant);
    }
    public RestaurantViewDto updateImage (MultipartFile file){
        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = user.getRestaurant();
        String path = imageFileUtils.saveFile(
                String.format("users/%d/",user.getId()),
                user.getUsername()+"restImage", file
        );
        restaurant.setRestImage(path);
        restRepository.save(restaurant);
        return RestaurantViewDto.fromEntity(restaurant);
    }









}
