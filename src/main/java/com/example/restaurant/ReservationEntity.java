package com.example.restaurant;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
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

/*
    private UserEntity user;

    private RestaurantEntity restaurant;*/


}
