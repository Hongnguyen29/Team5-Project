package com.example.restaurant.reservation.dto;

import com.example.restaurant.restaurants.entity.RestaurantEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationDto {

    private String nameCustom;
    private LocalDate date;
    private LocalTime time;
    private Integer peopleNumber;
    private String note;

}
