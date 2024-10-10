package com.example.restaurant.restaurants;

import com.example.restaurant.auth.AuthenticationFacade;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.auth.repo.UserRepository;
import com.example.restaurant.restaurants.dto.RestaurantDto;
import com.example.restaurant.restaurants.dto.RestaurantViewDto;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final UserRepository userRepository;
    private final RestaurantRepository restRepository;
    private final AuthenticationFacade facade;

    @Transactional
    public RestaurantViewDto updateInfo(RestaurantDto dto){
        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = user.getRestaurant();
        if(dto.getAddress() != null || !(dto.getAddress().isEmpty())) {
            restaurant.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null || !(dto.getPhone().isEmpty())){
            restaurant.setPhone(dto.getPhone());
        }
        if(dto.getDescription() != null || !(dto.getDescription().isEmpty())){
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
        restRepository.save(restaurant);

        return RestaurantViewDto.fromEntity(restaurant);
    }









}
