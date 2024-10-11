package com.example.restaurant.requestOpenClose.dto;

import com.example.restaurant.enumList.RequestStatus;
import com.example.restaurant.requestOpenClose.entity.CloseRequestEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CloseViewDto {
    private Long id;

    private String nameRestaurant;
    private String restNumber;
    private String ownerName;
    private String ownerIdNo;

    private String reason;

    private RequestStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processedAt;

    public static CloseViewDto fromEntity(CloseRequestEntity entity){
        return CloseViewDto.builder()
                .id(entity.getId())
                .nameRestaurant(entity.getRestaurant().getNameRestaurant())
                .restNumber(entity.getRestaurant().getRestNumber())
                .ownerName(entity.getRestaurant().getOwnerName())
                .ownerIdNo(entity.getRestaurant().getOwnerIdNo())
                .reason(entity.getReason())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .processedAt(entity.getProcessedAt())
                .build();


    }


}
