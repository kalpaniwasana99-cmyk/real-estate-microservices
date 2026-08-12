package com.realestate.user_auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // CSRF ආරක්ෂාව සම්පූර්ණයෙන්ම අක්‍රීය කිරීම
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // දැනට සියලුම Requests වලට අවසර දීම
            );
        return http.build();
    }
}