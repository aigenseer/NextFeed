package com.nextfeed.library.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*").allowedHeaders("*");
                registry.addMapping("/**").allowedMethods("*").allowedOrigins("*").allowedHeaders("*");
//                registry.addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowCredentials(true)
//                        .maxAge(3600)
//                        .allowedHeaders("Accept", "Content-Type", "Origin",
//                                "Authorization", "X-Auth-Token")
//                        .exposedHeaders("X-Auth-Token", "Authorization")
//                        .allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS");
            }
        };
    }
}
