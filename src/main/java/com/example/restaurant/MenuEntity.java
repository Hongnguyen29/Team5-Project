package com.example.restaurant;

import com.example.restaurant.restaurants.entity.RestaurantEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "resturant_id")
    private RestaurantEntity restaurant;

    @Builder
    public MenuEntity(String nameFood, Integer price) {
        this.nameFood = nameFood;
        this.price = price;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
        this.restaurant.getMenuList();
    }


    /*private RestaurantEntity restaurant;*/

}
