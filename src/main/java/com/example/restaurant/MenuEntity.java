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
public class MenuEntity extends BaseEntity{
    private String nameFood;
    private Integer price;
    private String image;
    private String status;  //


    private RestaurantEntity restaurant;

}
