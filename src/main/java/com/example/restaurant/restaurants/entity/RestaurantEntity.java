package com.example.restaurant.restaurants.entity;


import com.example.restaurant.reservation.entity.ReservationEntity;
import com.example.restaurant.review.entity.ReviewEntity;
import com.example.restaurant.support.BaseEntity;


import com.example.restaurant.support.BaseEntity;

import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.Category;
import com.example.restaurant.enumList.RestStatus;
import com.example.restaurant.menu.entity.MenuEntity;
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

    @Builder.Default
    @OneToMany(mappedBy = "restaurant",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<MenuEntity> menus = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private final List<ReservationEntity> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private final List<ReviewEntity> reviews = new ArrayList<>();







}

