package com.example.restaurant.user;


import com.example.restaurant.BaseEntity;
import com.example.restaurant.OpenRequestEntity;
import com.example.restaurant.restaurant.entity.RestaurantEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
@Entity
@Builder
public class UserEntity extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    @Setter
    @Column(unique = true)
    private String email;
    @Setter
    @Column(unique = true)
    private String phone;
    @Setter
    @Builder.Default
    private String role = "ROLE_USER";

    @Setter
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private RestaurantEntity restaurant;

    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private OpenRequestEntity openRequest;



}
