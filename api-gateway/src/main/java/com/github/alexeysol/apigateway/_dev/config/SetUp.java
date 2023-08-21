package com.github.alexeysol.apigateway._dev.config;

import com.github.alexeysol.apigateway.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class SetUp {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails adminUser = User.withDefaultPasswordEncoder()
            .username(ADMIN_USERNAME)
            .password(ADMIN_PASSWORD)
            .roles(String.valueOf(UserRole.ADMIN))
            .build();

        return new InMemoryUserDetailsManager(adminUser);
    }
}
