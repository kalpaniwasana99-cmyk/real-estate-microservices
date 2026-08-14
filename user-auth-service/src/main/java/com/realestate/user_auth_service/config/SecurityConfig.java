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
            .csrf(AbstractHttpConfigurer::disable) // CSRF ආරක්ෂාව අක්‍රීය කිරීම
            .authorizeHttpRequests(auth -> auth
                // Swagger UI සහ API Docs සඳහා ඕනෑම කෙනෙකුට අවසර දීම (අත්‍යවශ්‍යයි)
                .requestMatchers(
                    "/swagger-ui/**", 
                    "/v3/api-docs/**", 
                    "/swagger-ui.html",
                    "/auth/**" // Login සහ Register සඳහාද ඕනෑම කෙනෙකුට අවසර දීම
                ).permitAll()
                // වෙනත් ඕනෑම ඉල්ලීමකටද දැනට අවසර දීම
                .anyRequest().permitAll()
            );
            
        return http.build();
    }
}