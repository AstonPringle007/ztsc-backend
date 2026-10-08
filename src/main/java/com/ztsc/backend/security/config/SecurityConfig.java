package com.ztsc.backend.security.config;

import com.ztsc.backend.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // Disable CSRF because we are using JWT authentication
                .csrf(csrf -> csrf.disable())

                // Do not create server-side sessions
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // Public authentication APIs
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login"
                        ).permitAll()

                        // Procurement role
                        .requestMatchers("/api/procurement/**")
                        .hasAnyRole(
                                "SUPPLIER",
                                "PROCUREMENT",
                                "ADMIN"
                        )
                        .requestMatchers("/api/warehouse/**")
                        .hasAnyRole(
                                "WAREHOUSE",
                                "PROCUREMENT",
                                "ADMIN"
                        )
                        .requestMatchers("/api/finance/**")
                        .hasAnyRole(
                                "FINANCE",
                                "PROCUREMENT",
                                "ADMIN"
                        )
                        .requestMatchers("/api/security/**")
                        .hasAnyRole(
                                "ADMIN",
                                "AUDITOR",
                                "PROCUREMENT"
                        )
                        .requestMatchers("/api/audit/**")
                        .hasAnyRole(
                                "ADMIN",
                                "AUDITOR"
                        )
                        .requestMatchers("/api/dashboard/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PROCUREMENT",
                                "AUDITOR",
                                "FINANCE",
                                "WAREHOUSE",
                                "SUPPLIER"
                        )
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")
                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                // Run JWT filter before Spring's username/password filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}