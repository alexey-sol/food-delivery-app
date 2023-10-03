package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.feature.user.exception.UserAlreadyExistsException;
import com.github.alexeysol.common.feature.user.exception.UserUnauthorizedException;
import com.github.alexeysol.common.feature.user.model.dto.SignInDto;
import com.github.alexeysol.common.feature.user.model.dto.SignUpDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import com.github.alexeysol.common.shared.model.dto.InitDto;
import com.github.alexeysol.gateway.constant.AuthConstant;
import com.github.alexeysol.gateway.service.AuthService;
import com.github.alexeysol.gateway.service.LocalityService;
import com.github.alexeysol.gateway.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final LocalityService localityService;
    private final UserService userService;

    @GetMapping("/profile")
    public InitDto getProfile(@CookieValue(AuthConstant.AUTH_COOKIE_NAME) Optional<String> authToken) {
        var profile = authToken.map(authService::getProfileIfExists).orElse(null);
        var cities = localityService.getLocalities();
        return new InitDto(profile, cities);
    }

    @PostMapping("/sign-up")
    public UserDto signUp(@RequestBody @Valid SignUpDto dto, HttpServletResponse response) {
        var userDto = userService.getUserByPhone(dto.getPhone());

        if (Objects.nonNull(userDto)) {
            throw new UserAlreadyExistsException();
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
        var userDto = userService.getUserByPhone(dto.getPhone(), dto.getPassword());

        if (Objects.isNull(userDto)) {
            throw new UserUnauthorizedException();
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
