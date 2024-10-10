package com.example.restaurant.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private UserEntity entity;

    public static CustomUserDetails fromEntity(UserEntity entity) {
        return CustomUserDetails.builder()
                .entity(entity)
                .build();
    }
    @Override
    public String getUsername() {
        return this.entity.getUsername();
    }

    @Override
    public String getPassword() {
        return this.entity.getPassword();
    }
    public Long getId() {
        return this.entity.getId();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(entity.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }


}
