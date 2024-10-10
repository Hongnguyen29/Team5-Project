package com.example.restaurant.menu.dto;

import ch.qos.logback.core.status.Status;
import com.example.restaurant.menu.MenuEntity;
import lombok.*;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.io.ObjectInputFilter;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private Long id;
    private String nameFood;
    private int price;
    private String image;
    private Status status;


    public static MenuDto fromEntity(MenuEntity entity) {
        return MenuDto.builder()
                .id(entity.getId())
                .nameFood(entity.getNameFood())
                .price(entity.getPrice())
                .image(entity.getImage())
                .status(entity.getStatus())
                .build();
    }
}
