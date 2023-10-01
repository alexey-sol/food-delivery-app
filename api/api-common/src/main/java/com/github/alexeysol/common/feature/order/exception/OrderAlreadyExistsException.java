package com.github.alexeysol.common.feature.order.exception;

import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderAlreadyExistsException extends ResponseStatusException {
    public OrderAlreadyExistsException() {
        super(HttpStatus.CONFLICT, String.format(ErrorMessageConstant.ALREADY_EXISTS, ResourceConstant.ORDER));
    }
}
