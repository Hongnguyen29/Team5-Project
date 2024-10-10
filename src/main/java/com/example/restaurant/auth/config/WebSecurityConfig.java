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
                    auth.requestMatchers("/","static/**","/category"
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
                            "/auth/opens/readAll"

                    ).authenticated();

                    auth.requestMatchers("/openRequest"
                    ).hasRole("USER");

                    auth.requestMatchers("/rest/updateInfo"
                    ).hasRole("OWNER");

                    auth.requestMatchers(
                            "/admin/open/confirm/{openId}",
                            "/admin/opens/ReadAll"
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
