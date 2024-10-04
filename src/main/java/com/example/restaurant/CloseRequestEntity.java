package com.example.restaurant;

import com.example.restaurant.restaurant.entity.RestaurantEntity;
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


    private RestaurantEntity restaurant;




}
