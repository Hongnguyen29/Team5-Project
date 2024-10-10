package com.example.restaurant.restaurants.dto;

import com.example.restaurant.enumList.Category;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RestaurantDto {
    private String address;
    private String phone;
    private String description;
    private Category category;
    private LocalTime openTime;
    private LocalTime closeTime;

}
