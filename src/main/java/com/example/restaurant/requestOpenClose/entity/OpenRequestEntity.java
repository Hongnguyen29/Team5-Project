package com.example.restaurant.requestOpenClose.entity;

import com.example.restaurant.support.BaseEntity;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenRequestEntity extends BaseEntity {
    private String nameRestaurant;
    private String restNumber;
    private String imageRestNumber;  //
    private String ownerName;
    private String ownerIdNo;
    private String imageId;     //
    private String reason;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    //  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processedAt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;



}
