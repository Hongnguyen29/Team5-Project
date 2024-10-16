package com.example.restaurant.menu.dto;

import com.example.restaurant.enumList.MenuStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MenuDto {
    private Long id;
    private String nameFood;
    private Integer price;
    private MultipartFile file;
    private MenuStatus status;
}
