package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.model.ServicePage;
import com.github.alexeysol.common.model.dto.*;
import com.github.alexeysol.gateway.config.GatewayConfig;
import com.github.alexeysol.gateway.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@RequiredArgsConstructor
public class AuthController {
    private final static String CITY_RESOURCE = "city";
    private final static String USER_RESOURCE = "user";

    private final GatewayConfig config;
//    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @GetMapping("/profile") // TODO rename to init or smthng?
    public InitDto getProfile(@CookieValue("auth_token") Optional<String> authToken) {
        UserDto profile = null;

        // TODO what if authToken is null?
        if (authToken.isPresent() && jwtUtil.isValidAccessToken(authToken.get())) {
            var claims = jwtUtil.getSubject(authToken.get());
            var userIdAsString = claims.getSubject(); // get user id from jwt

            profile = config.appWebClient()
                .get()
                .uri(builder -> builder.pathSegment(USER_RESOURCE, userIdAsString).build())
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
        }

        var cities = config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(CITY_RESOURCE).build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Set<CityDto>>() {})
            .block();

        return new InitDto(profile, cities);
//        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "f off"); // TODO message
    }

    @PostMapping("/sign-up")
    public UserDto signUp(@RequestBody @Valid SignUpDto dto, HttpServletResponse response) {
//        try {
            // TODO check if pass and passConfirm match

//            Authentication authentication = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
//            );

            // TODO to private method
            var existingUserDto = config.appWebClient()
                .get()
                .uri(builder -> builder.pathSegment(USER_RESOURCE)
                    .queryParam("phone", dto.getPhone())
                    .build())
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();

            if (Objects.nonNull(existingUserDto)) {
//                var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, USER_RESOURCE, id); // TODO
                var message = "f off";
                throw new ResponseStatusException(HttpStatus.CONFLICT, message);
            }

            // TODO may return res 404 if city doen't exist
            var createdUserDto = config.appWebClient()
                .post()
                .uri(builder -> builder.pathSegment(USER_RESOURCE).build())
                .body(BodyInserters.fromValue(dto))
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();

            // TODO create user in App service

//            var principal = authentication.getPrincipal();
            if (Objects.nonNull(createdUserDto)) {
                // ...
                String accessToken = jwtUtil.generateAccessToken(createdUserDto);
                var authCookie = new Cookie("auth_token", accessToken);
                authCookie.setHttpOnly(true);
                authCookie.setMaxAge(24 * 60 * 60 * 1000); // TODO dayMs 24 * 60 * 60 * 1000; - probably should match jwt life period
                response.addCookie(authCookie);
                // ...

                return createdUserDto;
            }

            return null;


//            return ResponseEntity.ok().body(response);

//        } catch (BadCredentialsException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
    }
    // TODO
    //     private getMaxAge() {
    //        const dayInMs = 24 * 60 * 60 * 1000;
    //        const jwtExpiresIn = this.configService.get("auth.jwtExpiresIn", { infer: true });
    //        const isExpirationTimeInDays = jwtExpiresIn?.endsWith("d");
    //
    //        if (isExpirationTimeInDays) {
    //            const days = parseInt(jwtExpiresIn, 10);
    //            return days * dayInMs;
    //        }
    //
    //        return dayInMs;
    //    }

    @PostMapping("/sign-in")
    public UserDto signIn(@RequestBody @Valid SignInDto dto, HttpServletResponse response) {
        // TODO to private method
        var existingUserDto = config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(USER_RESOURCE)
                .queryParam("phone", dto.getPhone())
                .build())
            .retrieve()
            .bodyToMono(UserDto.class)
            .block();

        if (Objects.isNull(existingUserDto)) {
            var message = "f off";
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
        }

        // TODO check if pass matches

        // ...
        String accessToken = jwtUtil.generateAccessToken(existingUserDto);
        var authCookie = new Cookie("auth_token", accessToken);
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(24 * 60 * 60 * 1000); // TODO dayMs 24 * 60 * 60 * 1000; - probably should match jwt life period
        response.addCookie(authCookie);
        // ...

        return existingUserDto;
    }

    @PostMapping("/sign-out")
    public void signOut(@CookieValue("auth_token") Optional<String> authToken, HttpServletResponse response) {
        // TODO remove "auth_token" cookie
        if (authToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "f off"); // TODO message
        }

        // ...
//        String accessToken = jwtUtil.generateAccessToken(existingUserDto);
        var authCookie = new Cookie("auth_token", null);
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(0); // TODO dayMs 24 * 60 * 60 * 1000; - probably should match jwt life period
        response.addCookie(authCookie);
        // ...


    }
}
