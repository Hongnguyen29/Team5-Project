package com.example.restaurant.reservation.dto;

import com.example.restaurant.enumList.ReservationStatus;
import com.example.restaurant.reservation.entity.ReservationEntity;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationView {
    private Long id;
    private String nameCustom; //
    private LocalDate date;
    private LocalTime time;
    private Integer peopleNumber;
    private String note;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processedAt;
    private String reasonForRefusal;

    private String restaurantName;
    private String restaurantAddress;

    public static ReservationView fromEntity(ReservationEntity entity){
        return ReservationView.builder()
                .id(entity.getId())
                .nameCustom(entity.getNameCustom())
                .date(entity.getDate())
                .time(entity.getTime())
                .peopleNumber(entity.getPeopleNumber())
                .note(entity.getNote())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .processedAt(entity.getProcessedAt())
                .reasonForRefusal(entity.getReasonForRefusal())
                .restaurantName(entity.getRestaurant().getNameRestaurant())
                .restaurantAddress(entity.getRestaurant().getAddress())


                .build();




    }





}
