package com.realestate.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable()) // CSRF සම්පූර්ණයෙන්ම අක්‍රීය කිරීම
            .cors(cors -> {}) // Global CORS සක්‍රීය කිරීම
            .formLogin(form -> form.disable()) // Default Login Form අක්‍රීය කිරීම
            .httpBasic(basic -> basic.disable()) 
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/auth/**").permitAll()
                .anyExchange().permitAll()
            );
        return http.build();
    }
}