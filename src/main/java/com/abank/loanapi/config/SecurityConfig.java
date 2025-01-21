package com.abank.loanapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity (enable in production)
                .authorizeRequests()
                .requestMatchers(
                        "/swagger-ui/**", // Allow access to Swagger UI
                        "/v3/api-docs/**", // Allow access to Swagger API docs
                        "/h2-console/**" // Allow access to H2 Console
                ).permitAll() // Permit all requests to these endpoints
                .anyRequest().authenticated() // Secure all other endpoints
                .and()
                .httpBasic(); // Use HTTP Basic Authentication

        // Allow H2 Console to be displayed in frames (required for H2 Console)
        http.headers().frameOptions().disable();

        return http.build();
    }

}