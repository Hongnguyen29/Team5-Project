package com.example.restaurant.auth;




import com.example.restaurant.auth.entity.CustomUserDetails;
import com.example.restaurant.auth.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserEntity extractUser() {
        CustomUserDetails userDetails
                = (CustomUserDetails) getAuth().getPrincipal();
        return userDetails.getEntity();
    }

    public String findUsername(){
        return extractUser().getUsername();
    }
}