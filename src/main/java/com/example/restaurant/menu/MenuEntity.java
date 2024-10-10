package com.example.restaurant.menu;

import com.example.restaurant.BaseEntity;
import com.example.restaurant.restaurant.entity.RestaurantEntity;
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
    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    public enum Status {
        AVAILABLE,
        OUT_OF_STOCK
    }

}
