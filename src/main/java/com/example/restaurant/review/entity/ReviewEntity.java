package com.example.restaurant.review.entity;

import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.reservation.entity.ReservationEntity;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.support.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewEntity extends BaseEntity {
    private Integer star;
    private String content;
    private String image;
    private LocalDateTime timeCreate;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;


}
