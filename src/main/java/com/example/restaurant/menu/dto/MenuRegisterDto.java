package com.example.restaurant.menu.dto;

import com.example.restaurant.menu.MenuEntity;
import lombok.*;


@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuRegisterDto {
    private String nameFood;
    private int price;
    private String image;
    private MenuEntity.Status status;

    public MenuEntity toMenuEntity() {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setNameFood(this.nameFood);
        menuEntity.setPrice(this.price);
        menuEntity.setImage(this.image);
        menuEntity.setStatus(this.status);
        return menuEntity;
    }
}
