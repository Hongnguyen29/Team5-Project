package com.example.restaurant.requestOpenClose.entity;

import com.example.restaurant.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloseRequestEntity extends BaseEntity {
    private String reason;
    private String status;


    /*private RestaurantEntity restaurant;*/




}
