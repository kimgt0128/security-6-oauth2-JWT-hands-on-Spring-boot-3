package com.wondrous.oauth2_JWT.config;


import com.wondrous.oauth2_JWT.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpHeaders;

@EnableWebSecurity

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //OAuth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))));

        // 경로별 인가
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }
}
