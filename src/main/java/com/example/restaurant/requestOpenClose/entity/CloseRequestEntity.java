package com.example.restaurant.requestOpenClose.entity;

import com.example.restaurant.support.BaseEntity;
import com.example.restaurant.enumList.RequestStatus;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloseRequestEntity extends BaseEntity {
    private String reason;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    //  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processedAt;


    @OneToOne
    @JoinColumn(name = "closeRequest_id")
    private RestaurantEntity restaurant;

/*    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;*/


}
