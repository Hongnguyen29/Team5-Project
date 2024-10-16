package com.example.restaurant.menu.entity;

import com.example.restaurant.support.BaseEntity;
import com.example.restaurant.enumList.MenuStatus;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuEntity extends BaseEntity {
    private String nameFood;
    private Integer price;
    private String image;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MenuStatus status = MenuStatus.AVAILABLE;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;
}
