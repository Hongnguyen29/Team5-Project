package com.example.restaurant.restaurants.entity;

import com.example.restaurant.BaseEntity;
import com.example.restaurant.MenuEntity;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.Category;
import com.example.restaurant.enumList.RestStatus;
import com.example.restaurant.requestOpenClose.entity.CloseRequestEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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


    @Enumerated(EnumType.STRING)
    private RestStatus status;

    private String restImage;  //w---
    private String address;  // w
    private String phone;  //w
    private String description; //w

    @Enumerated(EnumType.STRING)
    private Category category;  //w

    private LocalTime openTime;  //w
    private LocalTime closeTime; //w

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(mappedBy = "restaurant")
    private CloseRequestEntity closeRequest;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<MenuEntity> menuList = new ArrayList<>();


}
