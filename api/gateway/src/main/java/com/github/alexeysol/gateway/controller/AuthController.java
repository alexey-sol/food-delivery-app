package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.constant.ErrorMessageConstant;
import com.github.alexeysol.common.model.dto.InitDto;
import com.github.alexeysol.common.model.dto.SignInDto;
import com.github.alexeysol.common.model.dto.SignUpDto;
import com.github.alexeysol.common.model.dto.UserDto;
import com.github.alexeysol.gateway.constant.AuthConstant;
import com.github.alexeysol.gateway.service.AuthService;
import com.github.alexeysol.gateway.service.CityService;
import com.github.alexeysol.gateway.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@RequiredArgsConstructor
public class AuthController {
    private final static String USER = "User";

    private final AuthService authService;
    private final CityService cityService;
    private final UserService userService;

    @GetMapping("/profile") // TODO rename to init or smthng?
    public InitDto getProfile(@CookieValue(AuthConstant.AUTH_COOKIE_NAME) Optional<String> authToken) {
        var profile = authToken.map(authService::getProfileIfAvailable).orElse(null);
        var cities = cityService.getAllCities();
        return new InitDto(profile, cities);
    }

    @PostMapping("/sign-up")
    public UserDto signUp(@RequestBody @Valid SignUpDto dto, HttpServletResponse response) {
        var userDto = userService.getUser(dto.getPhone());

        if (Objects.nonNull(userDto)) {
            var message = String.format(ErrorMessageConstant.ALREADY_EXISTS, USER);
            throw new ResponseStatusException(HttpStatus.CONFLICT, message);
        }

        var createdUserDto = userService.createUser(dto);

        if (Objects.nonNull(createdUserDto)) {
            response.addCookie(authService.getAuthCookie(createdUserDto));
            return createdUserDto;
        }

        return null;
    }

    @PostMapping("/sign-in")
    public UserDto signIn(@RequestBody @Valid SignInDto dto, HttpServletResponse response) {
        var userDto = userService.getUser(dto.getPhone(), dto.getPassword());

        if (Objects.isNull(userDto)) {
            var message = String.format(ErrorMessageConstant.INVALID_CREDENTIALS);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
        }

        response.addCookie(authService.getAuthCookie(userDto));
        return userDto;
    }

    @PostMapping("/sign-out")
    public boolean signOut(
        @CookieValue(AuthConstant.AUTH_COOKIE_NAME) Optional<String> authToken,
        HttpServletResponse response
    ) {
        if (authToken.isEmpty()) {
            return false;
        }

        response.addCookie(authService.getExpiredAuthCookie());
        return true;
    }
}
