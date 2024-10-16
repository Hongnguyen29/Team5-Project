package com.example.restaurant.restaurants.repo;

import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.Category;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long>,
        JpaSpecificationExecutor<RestaurantEntity> {


    List<RestaurantEntity> findByNameRestaurantContaining(String name);

    List<RestaurantEntity> findByAddressContaining(String address);
    boolean existsById(Long id);

    Optional<RestaurantEntity> findByUser(UserEntity user);




}
