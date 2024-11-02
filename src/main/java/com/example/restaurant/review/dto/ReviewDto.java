package com.example.restaurant.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewDto {

    private Integer star;
    private String content;
    private MultipartFile image;
    private LocalDateTime timeCreate;


}
