package com.example.restaurant.requestOpenClose.entity;

import com.example.restaurant.BaseEntity;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloseRequestEntity extends BaseEntity {
    private String reason;
    private String status;
    //  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processedAt;


    @OneToOne(mappedBy = "closeRequest")
    private RestaurantEntity restaurant;




}
