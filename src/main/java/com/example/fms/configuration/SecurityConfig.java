package com.example.fms.configuration;

import com.example.fms.service.InternalUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final InternalUserDetailsService internalUserDetailsService;

    public SecurityConfig(InternalUserDetailsService internalUserDetailsService) {
        this.internalUserDetailsService = internalUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authRequest -> authRequest
                    .requestMatchers(
                            "/api/schemes/**",
                            "/api/applications/**")
                    .hasRole("ADMIN")
                    .anyRequest().permitAll()
            )
                .userDetailsService(internalUserDetailsService)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
