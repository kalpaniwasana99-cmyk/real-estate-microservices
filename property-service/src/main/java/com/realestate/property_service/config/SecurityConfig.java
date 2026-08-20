package com.realestate.property_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Enable proper CORS integration so the browser doesn't block the frontend requests
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. Disable CSRF protection because this is a stateless REST API
            .csrf(csrf -> csrf.disable())
            
            // 3. Add your custom API Key Authentication Filter
            .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
            
            // 4. Configure endpoint access rules
            .authorizeHttpRequests(auth -> auth
                // CRITICAL FIX: Allow all OPTIONS (Preflight) requests to pass without checking the API Key. 
                // Browsers do not send headers like X-API-KEY during the OPTIONS preflight check.
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                
                // Allow all other incoming requests to pass through (The filter will still process them if needed)
                .anyRequest().permitAll()
            );

        return http.build();
    }

    // 5. Global CORS Configuration Bean
    // This explicitly tells the Spring Boot backend to accept requests from your HTML frontend
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow any frontend origin (e.g., http://localhost:5500, http://127.0.0.1:3000)
        configuration.setAllowedOrigins(Arrays.asList("*")); 
        
        // Allow all HTTP methods used by your frontend forms and fetch API
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); 
        
        // Allow all headers (Crucial for passing custom headers like 'X-API-KEY' or 'Authorization')
        configuration.setAllowedHeaders(Arrays.asList("*")); 
        
        // Apply this generous CORS policy to all API endpoints in the backend
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); 
        
        return source;
    }
}