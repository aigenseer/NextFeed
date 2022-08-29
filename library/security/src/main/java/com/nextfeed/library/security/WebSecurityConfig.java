package com.nextfeed.library.security;

import com.nextfeed.library.core.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final PreAuthTokenHeaderFilter preAuthTokenHeaderFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authorization-service/**").permitAll()
                .antMatchers("/api/session-service/presenter/**", "/api/survey-service/v1/presenter/").hasRole(UserRole.PRESENTER.getRole())
//                .antMatchers("/api/ws/**").authenticated()
                .anyRequest().authenticated()
                .and().addFilter(preAuthTokenHeaderFilter)
        ;
        return http.build();
    }

}
