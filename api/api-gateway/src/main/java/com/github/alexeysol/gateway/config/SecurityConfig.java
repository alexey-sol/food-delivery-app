package com.github.alexeysol.gateway.config;

import com.github.alexeysol.common.shared.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final GatewayProperties gatewayProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers.frameOptions().disable())
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        var adminProperties = gatewayProperties.getAdmin();

        UserDetails adminUser = User.withDefaultPasswordEncoder()
            .username(adminProperties.getUsername())
            .password(adminProperties.getPassword())
            .roles(String.valueOf(UserRole.ADMIN))
            .build();

        return new InMemoryUserDetailsManager(adminUser);
    }
}
