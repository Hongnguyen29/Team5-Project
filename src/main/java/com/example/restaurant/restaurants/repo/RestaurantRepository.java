package com.example.restaurant.restaurants.repo;

import com.example.restaurant.restaurants.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository
        extends JpaRepository<RestaurantEntity,Long> {
}
