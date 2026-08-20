package com.realestate.property_service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${app.api.key}")
    private String expectedApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestApiKey = request.getHeader("X-API-KEY");

        if (expectedApiKey.equals(requestApiKey)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized: Invalid API Key");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // /api/properties රූට් එක API Key පරීක්ෂාවෙන් නිදහස් කර ඇත
        return path.startsWith("/api/properties") || 
               path.startsWith("/api/properties/uploads/") || 
               path.startsWith("/swagger-ui/") ||
               path.startsWith("/v3/api-docs");
    }
}