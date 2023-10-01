package com.github.alexeysol.common.feature.user.exception;

import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserUnauthorizedException extends ResponseStatusException {
    public UserUnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, ErrorMessageConstant.INVALID_CREDENTIALS);
    }
}
