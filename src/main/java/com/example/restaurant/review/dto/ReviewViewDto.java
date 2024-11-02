package com.example.restaurant.review.dto;

import com.example.restaurant.review.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class ReviewViewDto {
    private Long id;
    private Integer star;
    private String content;
    private String image;
    private LocalDateTime timeCreate;

    private String username;

    public static ReviewViewDto fromEntity(ReviewEntity entity){
        log.info("loi dto entity");
        return ReviewViewDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .star(entity.getStar())
                .image(entity.getImage())
                .timeCreate(entity.getTimeCreate())
                .username((entity.getUser().getUsername()).substring(0,4) + "****")


                .build();
    }


}
