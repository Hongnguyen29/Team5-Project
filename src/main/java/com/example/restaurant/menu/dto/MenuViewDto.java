package com.example.restaurant.menu.dto;

import com.example.restaurant.enumList.MenuStatus;
import com.example.restaurant.menu.entity.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuViewDto {
    private Long id;
    private String nameFood;
    private Integer price;
    private String image;

    private MenuStatus status;

    public static MenuViewDto fromEntity(MenuEntity entity){
        return MenuViewDto.builder()
                .id(entity.getId())
                .nameFood(entity.getNameFood())
                .price(entity.getPrice())
                .image(entity.getImage())
                .status(entity.getStatus())
                .build();
    }
}
