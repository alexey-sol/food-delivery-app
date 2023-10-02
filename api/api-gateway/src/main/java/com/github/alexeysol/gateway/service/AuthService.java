package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.shared.exception.ServiceResponseException;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import com.github.alexeysol.gateway.constant.AuthConstant;
import com.github.alexeysol.gateway.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final static int EXPIRED_COOKIE_MAX_AGE_SECONDS = 0;

    @Value("${service.auth-cookie.max-age}")
    private int AUTH_COOKIE_MAX_AGE_SECONDS;

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserDto getProfileIfExists(String authToken) {
        if (jwtUtil.isValidAccessToken(authToken)) {
            var claims = jwtUtil.getSubject(authToken);

            try {
                var userId = Long.parseLong(claims.getSubject());
                return userService.getUserById(userId);
            } catch (ServiceResponseException ignored) {}
        }

        return null;
    }

    public Cookie getAuthCookie(UserDto dto) {
            String accessToken = jwtUtil.generateAccessToken(dto);
            var authCookie = new Cookie(AuthConstant.AUTH_COOKIE_NAME, accessToken);
            authCookie.setHttpOnly(true);
            authCookie.setMaxAge(AUTH_COOKIE_MAX_AGE_SECONDS);
            return authCookie;
    }

    public Cookie getExpiredAuthCookie() {
        var authCookie = new Cookie(AuthConstant.AUTH_COOKIE_NAME, null);
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(EXPIRED_COOKIE_MAX_AGE_SECONDS);
        return authCookie;
    }
}
