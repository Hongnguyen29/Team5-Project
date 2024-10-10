package com.example.restaurant.restaurants.dto;

import com.example.restaurant.enumList.Category;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantViewDto {
    private Long id;
    private String nameRestaurant;
    private String restNumber;
    private String ownerName;
    private String ownerIdNo;

    private String restImage;

    private String address;
    private String phone;
    private String description;
    private Category category;
    private LocalTime openTime;
    private LocalTime closeTime;

    public static RestaurantViewDto fromEntity(RestaurantEntity entity){
        return RestaurantViewDto.builder()
                .id(entity.getId())
                .nameRestaurant(entity.getNameRestaurant())
                .restNumber(entity.getRestNumber())
                .ownerName(entity.getOwnerName())
                .ownerIdNo(entity.getOwnerIdNo())

                .restImage(entity.getRestImage())

                .address(entity.getAddress())
                .phone(entity.getPhone())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .openTime(entity.getOpenTime())
                .closeTime(entity.getCloseTime())

                .build();

    }

}
