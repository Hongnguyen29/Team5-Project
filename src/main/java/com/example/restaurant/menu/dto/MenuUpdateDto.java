package com.example.restaurant.menu.dto;

import com.example.restaurant.MenuEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class MenuUpdateDto {
    @NotBlank(message = "Please add a menu")
    private String nameFood;

    @Range(min = 10000 , max = 100000, message = "Price from 10,000 to 100,000 won")
    private Integer price;

    private String image;
    private String status;

    public MenuEntity toEntity() {
        return MenuEntity.builder()
                .nameFood(nameFood)
                .price(price)
                .image(image)
                .status(status)
                .build();
    }
}
