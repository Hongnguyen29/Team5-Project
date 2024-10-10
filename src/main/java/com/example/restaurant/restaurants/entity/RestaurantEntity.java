package com.example.restaurant.restaurants.entity;

import com.example.restaurant.BaseEntity;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantEntity extends BaseEntity {

    private String nameRestaurant;
    private String restNumber;
    private String ownerName;
    private String ownerIdNo;

    private String restImage;  //w---
    private String address;  // w
    private String phone;  //w
    private String description; //w
    private Category category;  //w
    private LocalTime openTime;  //w
    private LocalTime closeTime; //w

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
