package com.example.restaurant;

import com.example.restaurant.restaurant.entity.RestaurantEntity;
import com.example.restaurant.user.UserEntity;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationEntity extends BaseEntity{
    private String nameCustom;
    private LocalDate date;
    private LocalTime time;
    private Integer peopleNumber;
    private String note;
    private String status;


    private UserEntity user;

    private RestaurantEntity restaurant;


}
