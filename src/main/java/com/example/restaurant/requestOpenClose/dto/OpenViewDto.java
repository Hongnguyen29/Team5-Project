package com.example.restaurant.requestOpenClose.dto;

import com.example.restaurant.requestOpenClose.entity.OpenRequestEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenViewDto {
    private Long id;
    private String nameRestaurant;
    private String restNumber;
    private String imageRestNumber;
    private String ownerName;
    private String ownerIdNo;
    private String imageId;
    private String reason;
    private String status;
//    private UserEntity user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processedAt;

    public static OpenViewDto fromEntity(OpenRequestEntity entity){
        return OpenViewDto.builder()
                .id(entity.getId())
                .nameRestaurant(entity.getNameRestaurant())
                .restNumber(entity.getRestNumber())
                .imageRestNumber(entity.getImageRestNumber())
                .ownerName(entity.getOwnerName())
                .ownerIdNo(entity.getOwnerIdNo())
                .imageId(entity.getImageId())
                .status(entity.getStatus())
                .reason(entity.getReason())
      //          .user(entity.getUser())
                .createdAt(entity.getCreatedAt())
                .processedAt(entity.getProcessedAt())
                .build();
    }

}
