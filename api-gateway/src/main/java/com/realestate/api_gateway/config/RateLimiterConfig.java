package com.realestate.api_gateway.config;

import com.realestate.api_gateway.filter.JwtFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    private final JwtFilter jwtFilter;

    public RateLimiterConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public KeyResolver userKeyResolver() {
        // IP ලිපිනය මත පදනම්ව සීමා කිරීම
        return exchange -> Mono.just(
            exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        // replenishRate: 1 (තත්පරයට 1යි), burstCapacity: 1
        return new RedisRateLimiter(1, 1, 1);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, RedisRateLimiter redisRateLimiter, KeyResolver userKeyResolver) {
        return builder.routes()
            .route("user-auth-service", r -> r.path("/auth/**")
                .filters(f -> f
                    .filter(jwtFilter.apply(new JwtFilter.Config()))
                    .requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter).setKeyResolver(userKeyResolver))
                )
                .uri("http://localhost:8081"))
            .build();
    }
}