package com.example.restaurant.review;

import com.example.restaurant.support.BaseEntity;
import jakarta.persistence.Entity;
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


/*    private UserEntity user;
    private RestaurantEntity restaurant;*/

}
