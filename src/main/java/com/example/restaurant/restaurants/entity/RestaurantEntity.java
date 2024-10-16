package com.example.restaurant.restaurants.entity;

<<<<<<< HEAD
import com.example.restaurant.support.BaseEntity;
=======
import com.example.restaurant.BaseEntity;
import com.example.restaurant.MenuEntity;
>>>>>>> 1e1386104fc269308dbf3d1dff1a09a058df938a
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

<<<<<<< HEAD
    @OneToMany(mappedBy = "restaurant",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<MenuEntity> menus = new ArrayList<>();
=======
    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<MenuEntity> menuList = new ArrayList<>();


>>>>>>> 1e1386104fc269308dbf3d1dff1a09a058df938a
}

