package com.example.restaurant.requestOpenClose.dto;

import com.example.restaurant.requestOpenClose.entity.CloseRequestEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String reason;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processedAt;

    public static CloseViewDto fromEntity(CloseRequestEntity entity){
        return CloseViewDto.builder()
                .id(entity.getId())
                .reason(entity.getReason())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .processedAt(entity.getProcessedAt())
                .build();


    }


}
