package com.example.restaurant.restaurant.entity;

import com.example.restaurant.BaseEntity;
import com.example.restaurant.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantEntity extends BaseEntity {

    private String name;
    private String restNumber;  //restaurant no
    private String address;
    private String role;
    private String ownerName;
    private String phone;
    private String description;
    private String openTime;
    private String closeTime;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menuList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
