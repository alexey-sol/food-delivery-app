package com.github.alexeysol.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

// TODO https://stackoverflow.com/questions/52143913/authentication-using-cookies-in-spring-boot
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
            .loginPage("/login")
            .usernameParameter("phone")
            .passwordParameter("password");

        return http


            .csrf(AbstractHttpConfigurer::disable)
            .headers(auth -> auth.frameOptions().disable())
//            .httpBasic(Customizer.withDefaults())
            .build();
    }
}
