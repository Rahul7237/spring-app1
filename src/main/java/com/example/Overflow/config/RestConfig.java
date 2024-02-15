package com.example.Overflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class RestConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        config.addAllowedOrigin("https://overflow-umber.vercel.app/"); // React app is running at this address

        config.addAllowedOrigin("https://react-overflow.vercel.app"); // React app is running at this address
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://overflow-hub.vercel.app");

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply CORS to all paths in the application
        return new CorsFilter(source);
    }
}