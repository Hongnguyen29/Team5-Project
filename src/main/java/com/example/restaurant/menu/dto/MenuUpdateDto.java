package com.example.restaurant.menu.dto;

import com.example.restaurant.menu.MenuEntity;
import lombok.*;


@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuUpdateDto {
    private Long id;
    private String nameFood;
    private int price;
    private String image;
    private MenuEntity.Status status;

    public MenuEntity toMenuEntity() {
        MenuEntity menu = new MenuEntity();
        menu.setNameFood(this.nameFood);
        menu.setPrice(this.price);
        menu.setImage(this.image);
        menu.setStatus(this.status);
        return menu;

    }
}
