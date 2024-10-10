package com.example.restaurant.requestOpenClose.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OpenDto {
    private String nameRestaurant;
    private String restNumber;
    private String ownerName;
    private String ownerIdNo;

    private MultipartFile imageRestNumber;
    private MultipartFile imageId;
}
