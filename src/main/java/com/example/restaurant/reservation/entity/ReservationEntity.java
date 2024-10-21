package com.example.restaurant.reservation.entity;

import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.ReservationStatus;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.support.BaseEntity;
import jakarta.persistence.*;
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
public class ReservationEntity extends BaseEntity {
    private String nameCustom;  // tên khách
    private LocalDate date; // ngày
    private LocalTime time;  // gio
    private Integer peopleNumber;  // số người
    private String note; // ghi chú

    private LocalDateTime createdAt; // time đặt bàn
    private LocalDateTime processedAt;  // time chủ quán xác nhận
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;  //
    private String reasonForRefusal;  // lý do chủ quán từ chối

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;


}
