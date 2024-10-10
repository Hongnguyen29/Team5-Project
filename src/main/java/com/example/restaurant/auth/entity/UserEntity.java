package com.example.restaurant.auth.entity;


import com.example.restaurant.BaseEntity;
import com.example.restaurant.requestOpenClose.entity.OpenRequestEntity;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
@Entity
@Builder
public class UserEntity extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private String image;

    @Builder.Default
    private String role = "ROLE_USER";

    @OneToOne(mappedBy = "user")
    private RestaurantEntity restaurant;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private final List<OpenRequestEntity> openRequests = new ArrayList<>();
    
/*
    @ManyToMany(mappedBy = "userLike")
    private final List<RestaurantEntity> restaurants = new ArrayList<>();
*/



}
