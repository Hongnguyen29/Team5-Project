package com.example.restaurant.auth.config;

import com.example.restaurant.auth.jwt.JwtTokenFilter;
import com.example.restaurant.auth.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService manager;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/view/**").permitAll();
                    auth.requestMatchers("/","static/**","/category",
                            "/restaurant/{restId}",
                            "/restaurant/{restId}/reservation",
                            "/search",
                            "/restaurant/page/{restId}",
                            "/restaurant/{restId}/menu",
                            "/restaurant/menu/{menuId}",
                            "/restaurant/{restId}/review",
                            "/restaurant/{restId}/star",
                            "/review/{reviewId}"

                    ).permitAll();

                    auth.requestMatchers("/login",
                            "/register"
                    ).anonymous();

                    auth.requestMatchers(
                            "/auth/profile",
                            "/auth/updateInfo",
                            "/auth/updateImage",
                            "/auth/password",
                            "/auth/opens/{openId}",
                            "/auth/opens/readAll",
                            "/auth/close/{closeId}",
                            "/auth/close/readAll",
                            "/myRestaurant",
                            "/auth/reservation/{reservationId}"  //nguoi dung hoặc
                            // chủ cửa hàng xem 1 lịch hẹn cụ thể

                    ).authenticated();

                    auth.requestMatchers(
                            "/user/openRequest",
                            "/user/reservation/{reservationId}",  // hủy lịch hẹn
                            "/user/reservation" , // nguoi dung xem toan bo lich hen minh da tao
                            //"/user/reservation/{reservationId}",
                            "/user/review/{reviewId}",
                            "/user/review"
                    ).hasRole("USER");

                    auth.requestMatchers(
                            "/rest/updateInfo",
                            "/rest/updateImg",
                            "rest/close",
                            "/rest/menu/{menuId}",
                            "/rest/menu",
                            "/rest/reservation/{reservationId}",  // xác nhận lịch hẹn ( đồng ý hoặc hủy)
                            "/rest/reservation", //cua hang xem toan bo lich hen cua quan
                            "/rest/complete/{reservationId}"


                    ).hasRole("OWNER");

                    auth.requestMatchers(
                            "/admin/open/confirm/{openId}",
                            "/admin/opens/ReadAll",
                            "/admin/close/confirm/{closeId}",
                            "/admin/close/ReadAll",
                            "/admin/users"
                    ).hasRole("ADMIN");


                })
                .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(
                        new JwtTokenFilter(
                                jwtTokenUtils,
                                manager
                        ),
                        AuthorizationFilter.class
                );
        return http.build();
    }
}
