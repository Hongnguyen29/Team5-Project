package com.example.restaurant.restaurant.entity;

import com.example.restaurant.BaseEntity;
import com.example.restaurant.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
